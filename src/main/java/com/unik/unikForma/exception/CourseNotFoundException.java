package com.unik.unikForma.exception;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(Long id) {
        super("Course not found with ID : " + id);
    }
}
