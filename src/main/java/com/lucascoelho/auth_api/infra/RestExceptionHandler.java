package com.lucascoelho.auth_api.infra;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UsernameNotFoundException.class)
    private ResponseEntity<RestErrorMessage> userNotFoundHandler(UsernameNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    private ResponseEntity<RestErrorMessage> badCredentialsHandler(BadCredentialsException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new RestErrorMessage(HttpStatus.UNAUTHORIZED, exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<RestErrorMessage> exceptionHandler(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));
    }
}
