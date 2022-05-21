package io.github.v1serviceapplication.global.error.handler;

import io.github.v1serviceapplication.global.error.exception.GlobalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({GlobalException.class})
    public ResponseEntity<ErrorResponse> handleGlobal(GlobalException e) {
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .status(e.getErrorAttribute().getStatus())
                        .message(e.getMessage())
                        .build(),
                HttpStatus.valueOf(e.getErrorAttribute().getStatus())
        );
    }

    @ExceptionHandler({BindException.class})
    public ResponseEntity<Map<String, String>> bindException(BindException e) {
        Map<String, String> errorMap = new HashMap<>();

        for (FieldError error : e.getFieldErrors()) {
            errorMap.put(error.getField(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity<ErrorResponse> noHandlerFoundException(NoHandlerFoundException e) {
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(e.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }



}
