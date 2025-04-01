package com.application.school.exception;

public class ProfessorNotFoundException extends RuntimeException {
    public ProfessorNotFoundException(String message) {
        super(message);
    }
}
