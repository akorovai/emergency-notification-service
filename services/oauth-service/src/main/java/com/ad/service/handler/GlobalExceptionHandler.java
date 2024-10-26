package com.ad.service.handler;

import lombok.Builder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handleAnyThrowable(Throwable throwable) {
        Throwable rootCause = getRootCause(throwable);
        return buildErrorResponse(rootCause);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(Throwable rootCause) {
        var status = ExceptionStatus.getStatusFor(rootCause);
        return ResponseEntity.status(status).body(ErrorResponse.builder()
                .message(rootCause.getMessage())
                .code(status.value())
                .build());
    }

    private Throwable getRootCause(Throwable throwable) {
        while (throwable.getCause() != null && throwable.getCause() != throwable) {
            throwable = throwable.getCause();
        }
        return throwable;
    }

    @Builder
    public record ErrorResponse(int code, String message) {
    }
}
