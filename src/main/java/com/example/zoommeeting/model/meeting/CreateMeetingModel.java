package com.example.zoommeeting.model.meeting;

import lombok.Data;

import static com.example.zoommeeting.config.meeting.MeetingConstants.INDIAN_TIME_ZONE;
import static com.example.zoommeeting.config.meeting.MeetingConstants.TYPE_SCHEDULED_MEETING;

@Data
public class CreateMeetingModel {

    private String agenda; // max 2000 chars

//    // Whether to generate a default passcode using the user's settings
//    @JsonProperty("default_password")
//    private boolean defaultPassword = false;

    private Integer duration; // durations in minutes
    private String password; // 10 chars max, alphanumeric, @, -, _, and * - validation default

//    @JsonProperty("pre_schedule")
    private boolean preSchedule = false; // TODO: when false meeting is crate don't know why

//    private Object recurrence; // optional
//
//    @JsonProperty("schedule_for")
//    private String scheduleFor; // optional

    private MeetingSettings settings = new MeetingSettings();

    // if null then turns into instant meeting
    // yyyy-MM-ddTHH:mm:ss or GMT yyyy-MM-ddTHH:mm:ssZ format
//    @JsonProperty("start_time")
    private String startTime;

    private String timezone = INDIAN_TIME_ZONE;
    private String topic; // max 200 char

//    // Information about the meeting's tracking fields.
//    @JsonProperty("tracking_fields")
//    private List<Object> trackingFields;

    private Integer type = TYPE_SCHEDULED_MEETING; // values in MeetingConstants.java

}
