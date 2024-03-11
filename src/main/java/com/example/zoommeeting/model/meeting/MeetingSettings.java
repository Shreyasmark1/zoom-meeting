package com.example.zoommeeting.model.meeting;

import com.example.zoommeeting.config.meeting.AudioType;
import com.example.zoommeeting.config.meeting.AutoRecording;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static com.example.zoommeeting.config.meeting.MeetingConstants.REGISTER_ONCE;

@Data
public class MeetingSettings {

//    @JsonProperty("additional_data_center_regions")
//    private String[] additionalDataCenterRegions;

//    Whether to allow attendees to join a meeting from multiple devices
//    @JsonProperty("allow_multiple_devices")
//    private boolean allowMultipleDevices = false;

//    @JsonProperty("alternative_hosts")
    private String alternativeHosts; // semicolon separated email

    // Whether to send email notifications to alternative hosts. default true
//    @JsonProperty("alternative_hosts_email_notification")
    private boolean alternativeHostsEmailNotification = true;

//    @JsonProperty("approval_type")
    private Integer approvalType; // values in MeetingConstants.java

//    @JsonProperty("approved_or_denied_countries_or_regions")
//    private Object approvedOrDeniedCountriesOrRegions;

    private AudioType audio = AudioType.both; // default

//    @JsonProperty("audio_conference_info")
//    private String audioConferenceInfo;

//    @JsonProperty("authentication_domains")
//    private String authenticationDomains;

//    A list of participants that can bypass meeting authentication.
//    @JsonProperty("authentication_exception")
//    private List<Object> authenticationException;

//    @JsonProperty("authentication_option")
//    private String authenticationOption;

//    @JsonProperty("auto_recording")
    private AutoRecording autoRecording = AutoRecording.cloud;

//    The pre-assigned breakout rooms settings.
//    @JsonProperty("breakout_room")
//    private Object breakoutRoom;

//    @JsonProperty("calendar_type")
//    private Integer calendarType; // values in ZoomMeetingConstants.java

//    @JsonProperty("close_registration")
//    private boolean closeRegistration = true;

//    The contact email address for meeting registration.
//    @JsonProperty("contact_email")
//    private String contactEmail;

//    @JsonProperty("contact_name")
//    private String contactName;

//    Whether to send email notifications to alternative hosts and users with scheduling privileges.
//    @JsonProperty("email_notification")
//    private boolean emailNotification;

//    @JsonProperty("encryption_type")
//    private EncryptionType encryptionType;

//    @JsonProperty("focus_mode")
//    private boolean focusMode;

//    Whether to start meetings with the host video on.
//    @JsonProperty("host_video")
//    private boolean hostVideo;

//    // If the value of the join_before_host field is true,
//    // this field indicates the time limits when a participant can join a meeting before the meeting's host.
//    @JsonProperty("jbh_time")
//    private Integer jbhTime; // values in ZoomMeetingConstants.java

//    Whether participants can join the meeting before its host.
//    @JsonProperty("join_before_host")
//    private boolean joinBeforeHost = false;

//    @JsonProperty("language_interpretation")
//    private Object languageInterpretation;

//    @JsonProperty("sign_language_interpretation")
//    private Object signLanguageInterpretation;

//    @JsonProperty("meeting_authentication")
    private boolean meetingAuthentication = false;

    // attendee/participants email
//    @JsonProperty("meeting_invitees")
    private List<MeetingInvitee> meetingInvitees = new ArrayList<>();

//    @JsonProperty("mute_upon_entry")
    private boolean muteUponEntry = true; // participants mic

//    @JsonProperty("participant_video")
    private boolean participantVideo = false;

//    @JsonProperty("private_meeting")
    private boolean privateMeeting = true;

    // Whether to send registrants an email confirmation.
//    @JsonProperty("registrants_confirmation_email")
    private boolean registrantsConfirmationEmail;

    // Whether to send registrants email notifications about their registration approval, cancellation, or rejection.
//    @JsonProperty("registrants_email_notification")
    private boolean registrantsEmailNotification = true;

//    @JsonProperty("registration_type")
    private Integer registrationType = REGISTER_ONCE; // values in MeetingConstants.java
}
