package com.unik.unikForma.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCourseNotFoundException(CourseNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClasseNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleClasseNotFoundException(ClasseNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LearnerNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleLearnerNotFoundException(LearnerNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InstructorNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleInstructorNotFoundException(InstructorNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());

        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }

        errorResponse.put("errors", fieldErrors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        return buildErrorResponse("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(String message, HttpStatus status) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("message", message);
        errorResponse.put("status", status.value());

        return new ResponseEntity<>(errorResponse, status);
    }
}
