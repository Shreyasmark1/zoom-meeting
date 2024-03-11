package com.example.zoommeeting.security.exception;

public class BaseApiException extends RuntimeException {

    public BaseApiException(){
        super("Something went wrong");
    }

    public BaseApiException(String message){
        super(message);
    }
}
