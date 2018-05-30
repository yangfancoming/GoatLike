package com.solituder.common;

import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * Error model for interacting with client.
 * 
 * @author vladimir.stankovic
 *
 * Aug 3, 2016
 */
public class ErrorResponse {

    private final HttpStatus status;  // HTTP Response Status Code
    private final String message; // General Error message
    private final ErrorCode errorCode; // Error code
    private final Date timestamp;

    protected ErrorResponse(final String message, final ErrorCode errorCode, HttpStatus status) {
        this.message = message;
        this.errorCode = errorCode;
        this.status = status;
        this.timestamp = new Date();
    }

    public static ErrorResponse of(final String message, final ErrorCode errorCode, HttpStatus status) {
        return new ErrorResponse(message, errorCode, status);
    }

    public Integer getStatus() {
        return status.value();
    }

    public String getMessage() {
        return message;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
