package com.ecommerce.backend.exceptions;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class GlobalExceptions extends RuntimeException {
    private HttpStatus httpStatus;

    public GlobalExceptions(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
