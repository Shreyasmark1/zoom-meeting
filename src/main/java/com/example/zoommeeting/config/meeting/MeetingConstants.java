package com.example.zoommeeting.config.meeting;

import com.example.zoommeeting.util.StringUtil;

public class MeetingConstants {

    // The user's user ID or email address. For user-level apps, pass "me" value.
    // returns unexpired scheduled meetings only
    public static final String GET_MEETINGS_URL = "/users/me/meetings";

    public static String getCreateMeetingUrl(String userId){
        userId = StringUtil.isEmpty(userId)? "me" : userId;

        return "/users/" + userId + "/meetings";
    }

    public static String getMeetingRecordingUrl(String meetingId){
        return "/meetings/" + meetingId + "/recordings";
    }

    public static final int MAX_RECORD_LIMIT = 300; // max records allowed
    public static final String INDIAN_TIME_ZONE = "Asia/Calcutta";

    public static final int TYPE_INSTANT_MEETING = 1; // An instant meeting.
    public static final int TYPE_SCHEDULED_MEETING = 2; // A scheduled meeting.
    public static final int TYPE_RECURRING_NO_FIXED_TIME = 3; // A recurring meeting with no fixed time.
    public static final int TYPE_RECURRING_FIXED_TIME = 8; // A recurring meeting with fixed time.

    // The meeting's registration type.
    public static final int REGISTER_ONCE = 1; // Attendees register once and can attend any meeting occurrence.
    public static final int REGISTER_EACH_MEETING = 2; // Attendees must register for each meeting occurrence.
    public static final int REGISTER_ONCE_AND_SELECT = 3; // Attendees register once and can select one or more meeting occurrences to attend.

    // Enable meeting registration approval. default 2 (no registration)
    public static final int APPROVE_AUTOMATIC = 0; // Automatically approve registration.
    public static final int APPROVE_MANUALLY = 1; // Manually approve registration.
    public static final int APPROVE_NO_REGISTRATION = 2; // No registration required.

    // Indicates the type of calendar integration used to schedule the meeting.
    public static final int ZOOM_CALENDER_OUTLOOK = 1;
    public static final int ZOOM_CALENDER_GOOGLE = 2;

    // Indicates the time limits when a participant can join a meeting before the meeting's host.
    // only these values are accepted
    public static final int JOIN_ANYTIME = 0;
    public static final int JOIN_5_MIN_BEFORE = 5;
    public static final int JOIN_10_MIN_BEFORE = 10;
}
