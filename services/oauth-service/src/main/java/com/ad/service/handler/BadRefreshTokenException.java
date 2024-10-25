package com.ad.service.handler;

public class BadRefreshTokenException extends RuntimeException {
    public BadRefreshTokenException(String message) {
        super(message);
    }
}
