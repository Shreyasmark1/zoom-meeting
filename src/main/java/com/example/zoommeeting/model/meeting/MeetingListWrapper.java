package com.example.zoommeeting.model.meeting;

import lombok.Data;

import java.util.List;

@Data
public class MeetingListWrapper {

    private String nextPageToken;
    private Integer pageCount;
    private Integer totalRecords;
    private List<Meeting> meetings;
}
