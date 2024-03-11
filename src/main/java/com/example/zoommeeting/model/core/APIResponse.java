package com.example.zoommeeting.model.core;

import static com.example.zoommeeting.config.Constants.FAILED;
import static com.example.zoommeeting.config.Constants.SUCCESS;

public class APIResponse {

    private final int status;
    private final String description;
    private Object data;

    private APIResponse(int status, String description) {
        this.status = status;
        this.description = description;
    }

    private APIResponse(int status, String description, Object data) {
        this.status = status;
        this.description = description;
        this.data = data;
    }

    private APIResponse(int status, String description, Object data, int recordCount) {
        this.status = status;
        this.description = description;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public static APIResponse success() {
        return new APIResponse(SUCCESS, "Completed Successfully", null);
    }

    public static APIResponse success(String message) {
        return new APIResponse(SUCCESS, message, null);
    }

    public static APIResponse success(int status, String message) {
        return new APIResponse(status, message);
    }

    public static APIResponse success(String message, Object data) {
        return new APIResponse(SUCCESS, message, data);
    }

    public static APIResponse success(String message, Object data, int recordCount) {
        return new APIResponse(SUCCESS, message, data, recordCount);
    }

    public static APIResponse send(Object data) {
        return new APIResponse(SUCCESS, "", data);
    }

    public static APIResponse send(Object data, int recordCount) {
        return new APIResponse(SUCCESS, "", data, recordCount);
    }

    public static APIResponse send(int status, Object data) {
        return new APIResponse(status, "", data);
    }

    public static APIResponse send(int status, Object data, int recordCount) {
        return new APIResponse(status, "", data, recordCount);
    }

    public static APIResponse failed(String message) {
        return new APIResponse(FAILED, message, null);
    }

    public static APIResponse failed(int status, String message) {
        return new APIResponse(status, message, null);
    }

    public static APIResponse failed(String message, Object data) {
        return new APIResponse(FAILED, message, data);
    }

    public static APIResponse failed() {
        return new APIResponse(FAILED, "Could not process the request", null);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
