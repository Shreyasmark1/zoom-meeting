package com.example.zoommeeting.security;

import com.example.zoommeeting.security.exception.BaseApiException;
import com.example.zoommeeting.util.StringUtil;
import com.example.zoommeeting.model.core.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.FileNotFoundException;
import java.util.Objects;


@ControllerAdvice
public class ExceptionControllerAdvice {

    private ResponseEntity<APIResponse> prepareResponse(String error, HttpStatus httpStatus) {
        return new ResponseEntity<>(APIResponse.failed(error), httpStatus);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<APIResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String name = ex.getName();
        String type = Objects.requireNonNull(ex.getRequiredType()).getSimpleName();
        Object value = ex.getValue();
        String message = String.format("Invalid input format for '%s'. Please check your input: '%s'", name, value);
//        String message = String.format("'%s' should be a valid '%s' but '%s' was received", name, type, value);
//        String message = "Invalid path or Invalid request param";
        return prepareResponse(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<APIResponse> exceptionHandler(Throwable ex) {

        String error = ex.getMessage();
        ResponseEntity<APIResponse> response;

        boolean logError = false;

        if (ex instanceof BaseApiException) {
            response = new ResponseEntity<>(APIResponse.failed(error), HttpStatus.OK);
        } else if (ex instanceof MissingServletRequestParameterException || ex instanceof MissingPathVariableException) {
//            response = new ResponseEntity<>(APIResponse.failed("Missing mandatory fields"), HttpStatus.BAD_REQUEST);
            response = new ResponseEntity<>(APIResponse.failed(error), HttpStatus.BAD_REQUEST);
        } else {
//            response = new ResponseEntity<>(APIResponse.failed(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()), HttpStatus.INTERNAL_SERVER_ERROR);
            response = new ResponseEntity<>(APIResponse.failed(error), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @ExceptionHandler({FileNotFoundException.class})
    public ResponseEntity<APIResponse> fileNotFoundException(FileNotFoundException ex) {
        return prepareResponse(StringUtil.findFileNameInPath(ex.getMessage()), HttpStatus.NOT_FOUND);
    }
}
