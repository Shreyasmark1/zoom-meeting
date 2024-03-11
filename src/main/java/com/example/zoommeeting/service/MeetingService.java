package com.example.zoommeeting.service;

import com.example.zoommeeting.config.meeting.MeetingStatus;
import com.example.zoommeeting.db.DatabaseMapper;
import com.example.zoommeeting.model.core.AppConfig;
import com.example.zoommeeting.security.exception.MeetingBaseException;
import com.example.zoommeeting.model.meeting.*;
import com.example.zoommeeting.util.Base64Util;
import com.example.zoommeeting.util.DateUtil;
import com.example.zoommeeting.util.JacksonUtil;
import com.example.zoommeeting.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static com.example.zoommeeting.config.Constants.ZOOM_ACCESS_TOKEN;
import static com.example.zoommeeting.config.Constants.ZOOM_REFRESH_TOKEN;
import static com.example.zoommeeting.config.GrantType.authorization_code;
import static com.example.zoommeeting.config.GrantType.refresh_token;
import static com.example.zoommeeting.config.meeting.MeetingConstants.*;

@Service
public class MeetingService {

    @Value("${app.zoom.api-base-url}")
    private String API_BASE_URL;

    @Value("${app.zoom.oauth-url}")
    private String OAUTH_URL;

    @Value("${app.zoom.client-id}")
    private String CLIENT_ID;

    @Value("${app.zoom.client-secret}")
    private String CLIENT_SECRET;

    @Value("${app.zoom.user-authorization-code}")
    private String USER_AUTHORIZATION_CODE;

    @Value("${app.zoom.user-code-verifier}")
    private String USER_CODE_VERIFIER;

    @Value("${app.zoom.auth-redirect-url}")
    private String AUTH_REDIRECT_URL;

    @Autowired
    private DatabaseMapper db;

    public MeetingListWrapper listMeetings(MeetingStatus status, Integer recLimit, Integer pageNumber, String nextPageToken, LocalDate from, LocalDate to){
        Map<String, Object> queryHashMap =  new HashMap<>();

        Optional.ofNullable(status).ifPresent(s -> queryHashMap.put("type", status));
        Optional.ofNullable(pageNumber).ifPresent(p -> queryHashMap.put("page_number", pageNumber));
        Optional.ofNullable(from).ifPresent(f -> queryHashMap.put("from", from));
        Optional.ofNullable(to).ifPresent(t -> queryHashMap.put("to", to));
        Optional.ofNullable(recLimit).map(r -> Math.min(r, MAX_RECORD_LIMIT)).ifPresent(r -> queryHashMap.put("page_size", r));
//        Optional.ofNullable(nextPageToken).ifPresent(nxtPgTkn -> queryHashMap.put("next_page_token", nextPageToken.trim()));
        if (StringUtil.isNotEmpty(nextPageToken))  queryHashMap.put("next_page_token", nextPageToken);

        String url = API_BASE_URL + GET_MEETINGS_URL + StringUtil.toQueryString(queryHashMap);

        HttpHeaders httpHeaders = getAuthHeader();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        String responseStr =  _sendApiRequest(url, HttpMethod.GET, httpHeaders, null);

        return JacksonUtil.snakeCaseStringToCamelCaseObject(responseStr, MeetingListWrapper.class);
    }

    public MeetingCreatedResponse createMeeting(CreateMeetingModel body){

        String url = API_BASE_URL + GET_MEETINGS_URL;

        HttpHeaders httpHeaders = getAuthHeader();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        String responseStr = _sendApiRequest(url, HttpMethod.POST, httpHeaders, JacksonUtil.camelCaseObjectToSnakeCaseString(body));

        return JacksonUtil.snakeCaseStringToCamelCaseObject(responseStr, MeetingCreatedResponse.class);
    }

    public MeetingRecording getMeetingRecording(String meetingId){

        String url = API_BASE_URL + getMeetingRecordingUrl(meetingId);

        HttpHeaders httpHeaders = getAuthHeader();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        String responseStr = _sendApiRequest(url, HttpMethod.GET, httpHeaders, null);

        return JacksonUtil.snakeCaseStringToCamelCaseObject(responseStr, MeetingRecording.class);
    }

    private HttpHeaders getAuthHeader(){
        HttpHeaders httpHeaders = new HttpHeaders();

        AppConfig appConfig = db.getAppConfigValue(ZOOM_ACCESS_TOKEN);
        // if null then get new access token
        if (appConfig == null){
            getAccessToken(false);
        } else {

            // check last token updated time
            LocalDateTime tokenUpdatedOn = DateUtil.mysqlDateTimeToLocalDateTime(appConfig.getUpdatedAt());

            // token will have 1 hour validity
            LocalDateTime oneHourLater = tokenUpdatedOn.plusMinutes(55);

            if (LocalDateTime.now().isAfter(oneHourLater)){
                // refresh the token
                getAccessToken(true);
            }
        }

        httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + db.getAppConfigValue(ZOOM_ACCESS_TOKEN).getValue());
        return httpHeaders;
    }

    private void getAccessToken(boolean isRefresh){
        Map<String, Object> formData = new HashMap<>();

        if (isRefresh){
            formData.put("grant_type", refresh_token);
            formData.put("refresh_token", db.getAppConfigValue(ZOOM_REFRESH_TOKEN).getValue());
        } else {
            formData.put("code", USER_AUTHORIZATION_CODE);
            formData.put("grant_type", authorization_code);
            formData.put("redirect_uri", AUTH_REDIRECT_URL);
            formData.put("code_verifier", USER_CODE_VERIFIER);
        }

        String url = OAUTH_URL + StringUtil.toQueryString(formData);

        String responseStr = _sendApiRequest(url, HttpMethod.POST, geOAuthHeader(), null);

        OAuthResponse response = JacksonUtil.snakeCaseStringToCamelCaseObject(responseStr, OAuthResponse.class);
        assert response != null;

        if (isRefresh){
            db.updateAppConfig(response.getAccessToken(), ZOOM_ACCESS_TOKEN);
            db.updateAppConfig(response.getRefreshToken(), ZOOM_REFRESH_TOKEN);
        } else {
            db.insertIntoAppConfig(ZOOM_ACCESS_TOKEN, response.getAccessToken(), "Zoom access token");
            db.insertIntoAppConfig(ZOOM_REFRESH_TOKEN, response.getRefreshToken(), "Zoom refresh token");
        }
    }

    private HttpHeaders geOAuthHeader(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(Base64Util.basicAuthEncoder(CLIENT_ID, CLIENT_SECRET));
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        return httpHeaders;
    }

    private String _sendApiRequest(String url, HttpMethod method, HttpHeaders httpHeaders, String payload){

        RestTemplate template = new RestTemplate();

        HttpEntity<String> entity = new HttpEntity<>(payload, httpHeaders);

        try{

            ResponseEntity<String> response = template.exchange(url, method, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) return response.getBody();
            else throw new MeetingBaseException(response.getBody());

        } catch (Exception e){
//            e.printStackTrace();
            throw new MeetingBaseException(e.getMessage());
        }
    }

    private String _createChallenge() throws NoSuchAlgorithmException {
        // sed to create code challenge for requesting user authorization
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(USER_CODE_VERIFIER.getBytes(StandardCharsets.US_ASCII));
        byte[] digest = md.digest();
        return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
    }
}
