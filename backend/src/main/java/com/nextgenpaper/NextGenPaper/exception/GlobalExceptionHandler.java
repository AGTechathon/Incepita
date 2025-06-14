package com.nextgenpaper.NextGenPaper.exception;

import com.nextgenpaper.NextGenPaper.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException e, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now().toString());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(ResourceAllReadExistException.class)
    public ResponseEntity<ErrorResponse> handleResourceAllReadyExistException(ResourceAllReadExistException e, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now().toString());
        errorResponse.setStatus(HttpStatus.OK.value());
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }

}
