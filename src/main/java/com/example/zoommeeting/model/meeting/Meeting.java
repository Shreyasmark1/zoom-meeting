package com.example.zoommeeting.model.meeting;

import lombok.Data;

import java.math.BigInteger;

@Data
public class Meeting {
    private String uuid;
    private BigInteger id;
    private String hostId;
    private String topic;
    private Integer type;
    private String startTime;
    private Integer duration;
    private String timezone;
    private String createdAt;
    private String joinUrl;
    private String pmi;
}
