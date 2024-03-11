package com.example.zoommeeting.model.meeting;

import lombok.Data;

import java.util.List;

@Data
public class MeetingRecording {

    private Integer duration;
    private Integer recordingCount;
    private String recordingPlayPasscode;
    private List<RecordingFile> recordingFiles;
    private List<RecordingFile> participantAudioFiles;

}
