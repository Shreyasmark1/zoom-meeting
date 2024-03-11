package com.example.zoommeeting.security.exception;

public class MeetingBaseException extends BaseApiException {

    public MeetingBaseException(){
        super("something went wrong");
    }

    public MeetingBaseException(String message){
        super(message);
    }

}
