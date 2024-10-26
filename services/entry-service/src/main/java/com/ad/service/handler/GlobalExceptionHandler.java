package com.ad.service.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.Builder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();


    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponse> handleFeignException(FeignException ex) {
        int status = ex.status();
        String message = extractMessageFromFeignException(ex);
        return buildErrorResponse(status, message);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(int status, String message) {
        return ResponseEntity.status(status).body(
                ErrorResponse.builder()
                        .message(message)
                        .code(status)
                        .build()
        );
    }

    private String extractMessageFromFeignException(FeignException ex) {
        try {
            JsonNode errorBody = objectMapper.readTree(ex.contentUTF8());
            return errorBody.path("message").asText("An unexpected error occurred");
        } catch (Exception parseException) {
            return "An error occurred while processing the response";
        }
    }

    @Builder
    public record ErrorResponse(int code, String message) {
    }
}
