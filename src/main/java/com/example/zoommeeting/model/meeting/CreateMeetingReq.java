package com.example.zoommeeting.model.meeting;

import lombok.Data;

@Data
public class CreateMeetingReq {

    private int hours;
    private int minutes;
    private int duration;
    private String uniqueId;
}
