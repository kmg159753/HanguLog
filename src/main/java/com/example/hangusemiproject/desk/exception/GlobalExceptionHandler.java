package com.example.hangusemiproject.desk.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<CustomException> illegalArgumentExceptionHandler(IllegalArgumentException ex) {
        CustomException customException = new CustomException("fail",ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                customException,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<CustomException> nullPointerExceptionHandler(NullPointerException ex) {
        CustomException customException = new CustomException("fail",ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                customException,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class})
    public ResponseEntity<CustomException> exceptionHandler(Exception ex) {
        String message = ex.getMessage();

        if (ex instanceof MethodArgumentNotValidException) {
            message = ((MethodArgumentNotValidException) ex).getBindingResult().getFieldError().getDefaultMessage();
        }

        CustomException customException = new CustomException("fail",message, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                customException,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({ExpiredJwtException.class})
    public ResponseEntity<CustomException> expiredJwtExceptionHandler(ExpiredJwtException ex) {
        CustomException customException = new CustomException("fail", "Token expired", HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(
                customException,
                HttpStatus.UNAUTHORIZED
        );
    }
}
