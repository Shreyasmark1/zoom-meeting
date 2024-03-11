package com.example.zoommeeting.model.meeting;

import com.example.zoommeeting.config.meeting.RecordingFileType;
import lombok.Data;

@Data
public class RecordingFile {

    private String fileName;
    private String downloadUrl;
    private String playUrl;
    private RecordingFileType fileType;
}
