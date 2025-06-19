package com.hotel.web;

import java.time.LocalDateTime;

public record ErrorResponse(String message, int status, String timestamp) {

    public static ErrorResponse of(String message, int status) {
        return new ErrorResponse(message, status, LocalDateTime.now().toString());
    }
}
