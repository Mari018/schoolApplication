package com.application.school.exception.exceptionHndler;

import com.application.school.exception.EmailAlreadyExistsException;
import com.application.school.exception.StudentNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Date;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {
            StudentNotFoundException.class,
            EmailAlreadyExistsException.class })
    public ResponseEntity<com.application.school.exception.exceptionHndler.Error> handleNotFound(Exception e, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Error.builder()
                .timeStamp(new Date())
                .message(e.getMessage())
                .method(request.getMethod())
                .requestURI(request.getRequestURI())
                .build());


    }
}
