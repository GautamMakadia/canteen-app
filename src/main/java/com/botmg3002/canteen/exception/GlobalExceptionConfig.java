package com.botmg3002.canteen.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionConfig {
    
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentails(BadCredentialsException ex) {
        System.out.println("Error:"+ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyFound(UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(Map.of("error", ex.getMessage()));
    }
}
