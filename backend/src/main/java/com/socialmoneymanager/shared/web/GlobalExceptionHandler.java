package com.socialmoneymanager.shared.web;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception exception) {
        String requestId = UUID.randomUUID().toString();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(new ErrorBody("INTERNAL_ERROR", "An unexpected error occurred.", requestId, List.of())));
    }

    public record ErrorResponse(ErrorBody error) {}
    public record ErrorBody(String code, String message, String requestId, List<String> details) {}
}
