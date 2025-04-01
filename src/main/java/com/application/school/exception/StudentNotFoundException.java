package com.application.school.exception;

public class StudentNotFoundException extends RuntimeException {

  public StudentNotFoundException(String message) {
        super(message);
    }
}
