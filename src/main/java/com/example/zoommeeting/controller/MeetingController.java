package com.example.zoommeeting.controller;

import com.example.zoommeeting.config.meeting.MeetingStatus;
import com.example.zoommeeting.model.core.APIResponse;
import com.example.zoommeeting.model.meeting.CreateMeetingModel;
import com.example.zoommeeting.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/meeting")
public class MeetingController {

    @Autowired
    MeetingService meetingService;

    @GetMapping("/list")
    public APIResponse getMeetings(
            @RequestParam(value = "status", required = false) MeetingStatus status,
            @RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "recLimit", required = false) Integer recLimit,
            @RequestParam(value = "nextPageToken", required = false) String nextPageToken
    ){
        return APIResponse.send(meetingService.listMeetings(status, recLimit, pageNumber, nextPageToken, from, to));
    }

    @GetMapping("/{meetingId}/recording")
    public APIResponse getMeetingRecording(
            @PathVariable(value = "meetingId") String meetingId
    ){
        return APIResponse.send(meetingService.getMeetingRecording(meetingId));
    }

    @PostMapping("/create")
    public APIResponse createMeeting(@RequestBody CreateMeetingModel body) {
        return APIResponse.send(meetingService.createMeeting(body));
    }

}
