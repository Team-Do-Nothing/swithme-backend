package com.donothing.swithme.common;

import com.amazonaws.services.kms.model.NotFoundException;
import com.donothing.swithme.dto.response.ErrorMessage;
import com.donothing.swithme.exception.ChallengeAlreadyJoinedException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorMessage> IllegalStateException(IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorMessage.builder()
                        .message(e.getMessage())
                        .code(HttpStatus.INTERNAL_SERVER_ERROR)
                        .build());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorMessage> NoSuchElementException(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessage.builder()
                .message(e.getMessage())
                .code(HttpStatus.BAD_REQUEST)
                .build());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorMessage> NoHandlerFoundException(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessage.builder()
                .message(message)
                .code(HttpStatus.NOT_FOUND)
                .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> errors = new HashMap<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessage.builder()
                .message(errors.toString())
                .code(HttpStatus.BAD_REQUEST)
                .build());
    }

    @ExceptionHandler(ChallengeAlreadyJoinedException.class)
    public ResponseEntity<ErrorMessage> handleChallengeAlreadyJoinedException(ChallengeAlreadyJoinedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorMessage.builder()
                .message(ex.getMessage())
                .code(HttpStatus.CONFLICT)
                .build());
    }
}
