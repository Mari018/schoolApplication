package com.application.school.exception;

public class EmailAlreadyExistsException extends RuntimeException
{
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
