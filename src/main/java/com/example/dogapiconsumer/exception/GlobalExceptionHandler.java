package com.example.dogapiconsumer.exception;

import com.example.dogapiconsumer.model.ApiResponse;
import com.example.dogapiconsumer.model.DogImageResponse;
import com.example.dogapiconsumer.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final DogService dogService;

    @Autowired
    public GlobalExceptionHandler(DogService dogService) {
        this.dogService = dogService;
    }

    @ExceptionHandler(HttpStatusCodeException.class)
    public ResponseEntity<ApiResponse<Object>> handleHttpStatusCodeException(HttpStatusCodeException ex) {
        return createErrorResponse(ex.getStatusCode(), ex.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return createErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneralException(Exception ex) {
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    private ResponseEntity<ApiResponse<Object>> createErrorResponse(HttpStatusCode status, String message) {
        DogImageResponse imageResponse = dogService.getHttpDogImage(status.value());

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("error", status.toString());
        errorDetails.put("message", message);

        ApiResponse<Object> response = new ApiResponse<>(errorDetails, imageResponse);
        return new ResponseEntity<>(response, status);
    }
}
