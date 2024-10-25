package com.ad.service.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.IdentityHashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public enum ExceptionStatus {
    BAD_REFRESH_TOKEN(BadRefreshTokenException.class, HttpStatus.BAD_REQUEST),

    EXISTING_USER_EXCEPTION(ExistingUserException.class, HttpStatus.CONFLICT),

    BAD_CREDENTIALS(BadCredentialsException.class, UNAUTHORIZED),

    ;


    private final Class<? extends Throwable> exceptionClass;
    private final HttpStatus status;

    ExceptionStatus(Class<? extends Throwable> exceptionClass, HttpStatus httpStatus) {
        this.exceptionClass = exceptionClass;
        this.status = httpStatus;
    }

    private static final Map<Class<? extends Throwable>, HttpStatus> EXCEPTION_STATUS_MAP;

    static {
        EXCEPTION_STATUS_MAP = new IdentityHashMap<>(values().length);
        for (ExceptionStatus status : values()) {
            EXCEPTION_STATUS_MAP.put(status.exceptionClass, status.status);
        }
    }

    public static HttpStatus getStatusFor(Throwable throwable) {
        Class<?> exceptionClass = throwable.getClass();
        HttpStatus status;
        do {
            status = EXCEPTION_STATUS_MAP.get(exceptionClass);
            if (status != null) {
                return status;
            }
            exceptionClass = exceptionClass.getSuperclass();
        } while (exceptionClass != null);
        return INTERNAL_SERVER_ERROR;
    }

}
