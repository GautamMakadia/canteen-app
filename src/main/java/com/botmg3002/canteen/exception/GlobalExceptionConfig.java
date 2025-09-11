package com.botmg3002.canteen.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.NotAcceptableStatusException;
import org.springframework.web.server.ResponseStatusException;

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

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
        .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(NotAcceptableStatusException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(NotAcceptableStatusException ex) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
        .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode())
        .body(Map.of("error", ex.getReason()));
    }
    
}
