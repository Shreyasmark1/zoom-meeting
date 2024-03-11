package com.example.zoommeeting.model.meeting;

import lombok.Data;

import java.math.BigInteger;

@Data
public class MeetingCreatedResponse {

    private BigInteger id;
    private String password;
//    @JsonProperty("start_url")
    private String startUrl;
//    @JsonProperty("join_url")
    private String joinUrl;

    // omitted not required field
}
