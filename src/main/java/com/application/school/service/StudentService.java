package com.application.school.service;

import com.application.school.dto.StudentDto;
import com.application.school.entity.Student;
import com.application.school.exception.EmailAlreadyExistsException;
import com.application.school.exception.StudentNotFoundException;
import com.application.school.mapper.StudentMapper;
import com.application.school.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public List<StudentDto> getStudents() {
        List<Student> students = new ArrayList<>(this.studentRepository.findAll());

        LOGGER.info("returned a list of students (empty or not). {}", students);
        return StudentMapper.INSTANCE.listStudentToStudentsDto(students);
    }


    public StudentDto getStudent(Long id) {
        Student student = this.studentRepository.findById(id).orElseThrow(()-> {
            LOGGER.error("student with id: .{} don't exist", id);
            return new StudentNotFoundException("Student not found");
        });

        LOGGER.info("return a user by their id. {}", student);
        return StudentMapper.INSTANCE.studentToStudentDto(student);
    }


    public Long addStudent(StudentDto studentDto) {
        Student student = StudentMapper.INSTANCE.studentDtoToStudent(studentDto);

       if (studentRepository.findByEmail(studentDto.getEmail()).isPresent()) {
           LOGGER.error("this email is already in use .{}", studentDto.getEmail());
            throw new EmailAlreadyExistsException("this email already exists");
        }

       LOGGER.info("save this new student on the repository .{}",student.toString());
       return this.studentRepository.save(student).getId();
    }
}

