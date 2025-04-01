package com.application.school.controller;


import com.application.school.dto.StudentDto;
import com.application.school.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/v1")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(path = "/student")
    @PreAuthorize("@authorized.hasRole(\"ADMIN\")")
    public ResponseEntity<List<StudentDto>> getStudents(){
        return ResponseEntity.ok().body(studentService.getStudents());
    }

    @GetMapping(path = "/student/{id}")
    @PreAuthorize("@authorized.hasRole(\"ADMIN\")")
    public ResponseEntity<StudentDto> getStudent(Long id){

        return ResponseEntity.ok().body(studentService.getStudent(id));
    }


    @PostMapping(path = "/student")
    @PreAuthorize("@authorized.hasRole(\"ADMIN\")")
    public ResponseEntity<Long> addStudent(@Valid @RequestBody StudentDto studentDto){

        return ResponseEntity.ok().body(studentService.addStudent(studentDto));
    }
}
