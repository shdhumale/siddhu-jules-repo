package com.example.dogapiconsumer.exception;

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

@ControllerAdvice
public class GlobalExceptionHandler {

    private final DogService dogService;

    @Autowired
    public GlobalExceptionHandler(DogService dogService) {
        this.dogService = dogService;
    }

    @ExceptionHandler(HttpStatusCodeException.class)
    public ResponseEntity<DogImageResponse> handleHttpStatusCodeException(HttpStatusCodeException ex) {
        return createErrorResponse(ex.getStatusCode());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<DogImageResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return createErrorResponse(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DogImageResponse> handleGeneralException(Exception ex) {
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<DogImageResponse> createErrorResponse(HttpStatusCode status) {
        DogImageResponse response = dogService.getHttpDogImage(status.value());
        return new ResponseEntity<>(response, status);
    }
}
