package com.application.school.service;

import com.application.school.dto.StudentDto;
import com.application.school.entity.Course;
import com.application.school.entity.Student;
import com.application.school.enums.CourseType;
import com.application.school.enums.GenderType;
import com.application.school.exception.EmailAlreadyExistsException;
import com.application.school.exception.StudentNotFoundException;
import com.application.school.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void before() {
        System.setProperty("mockito.mock.maker", "mock-maker-default");
    }

    @Test
    void When_getStudents_then_return(){
        List<Student> students = new ArrayList<>();

        when(studentRepository.findAll()).thenReturn(students);

        studentService.getStudents();

        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void When_getStudent_then_return(){
        Student student = new Student();
        Optional<Student> optional = Optional.of(student);

        when(studentRepository.findById(any())).thenReturn(optional);

        studentService.getStudent(any());

        verify(studentRepository, times(1)).findById(any());

    }

    @Test
    void When_getStudent_then_returnNull(){

        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentService.getStudent(1L));

        verify(studentRepository, times(1)).findById(any());

    }

    @Test
    void When_addStudent_then_return(){
        StudentDto studentDto = new StudentDto("testname","testLname", GenderType.MALE,new Date(),"test@example.com", new Course());
        Student student = new Student();
        student.setFirstName("testname");
        student.setLastName("testLname");
        student.setGender(GenderType.MALE);
        student.setEmail("test@example.com");
        student.setCourse(studentDto.getCourse());

        when(studentRepository.findByEmail(studentDto.getEmail())).thenReturn(Optional.empty());
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        studentService.addStudent(studentDto);

        verify(studentRepository, times(1)).findByEmail(studentDto.getEmail());
        verify(studentRepository, times(1)).save(any(Student.class));

        assertNotNull(studentDto);
        assertEquals(studentDto.getFirstName(), student.getFirstName());
        assertEquals(studentDto.getLastName(), student.getLastName());
        assertEquals(studentDto.getGender(), student.getGender());
        assertEquals(studentDto.getEmail(), student.getEmail());
        assertEquals(studentDto.getCourse(), student.getCourse());

    }

    @Test
    void When_addStudent_then_returnError(){
        StudentDto studentDto = new StudentDto("testname","testLname", GenderType.MALE,new Date(),"test@example.com", new Course());
        Student student = new Student();
        student.setFirstName("testname");
        student.setLastName("testLname");
        student.setGender(GenderType.MALE);
        student.setEmail("test@example.com");
        student.setCourse(studentDto.getCourse());

        when(studentRepository.findByEmail(studentDto.getEmail())).thenReturn(Optional.of(student));

        assertThrows(EmailAlreadyExistsException.class, () -> studentService.addStudent(studentDto));

        verify(studentRepository, times(1)).findByEmail(studentDto.getEmail());
        verify(studentRepository, times(0)).save(any(Student.class));

        assertEquals(studentDto.getEmail(), student.getEmail());
    }
}