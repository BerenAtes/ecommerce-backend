package com.ecommerce.backend.exceptions;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> exceptionHandler(GlobalExceptions globalExceptions){
        log.error("Global error occurred. Error message: " + globalExceptions.getMessage());
        ErrorResponse errorResponse=new ErrorResponse(globalExceptions.getHttpStatus().value(),globalExceptions.getMessage(),System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse,globalExceptions.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception exception){
        log.error("Error occurred. Error message: " + exception.getMessage());
        ErrorResponse errorResponse=new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),exception.getMessage(),System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
