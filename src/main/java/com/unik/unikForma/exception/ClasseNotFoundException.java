package com.unik.unikForma.exception;

public class ClasseNotFoundException extends RuntimeException {
    public ClasseNotFoundException(Long id) {
        super("Classe not found with ID: " + id);
    }
}