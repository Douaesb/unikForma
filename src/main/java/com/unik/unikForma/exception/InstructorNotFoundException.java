package com.unik.unikForma.exception;

public class InstructorNotFoundException extends RuntimeException {
    public InstructorNotFoundException(Long id) {
        super("Instructor not found with id: " + id);
    }
}
