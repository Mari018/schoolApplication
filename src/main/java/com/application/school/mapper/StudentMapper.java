package com.application.school.mapper;

import com.application.school.dto.StudentDto;
import com.application.school.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    Student studentDtoToStudent (StudentDto studentDto);

    StudentDto studentToStudentDto (Student student);

    List<Student> listStudentDtoToListStudents (List<StudentDto> studentsDto);

    List<StudentDto> listStudentToStudentsDto (List<Student> students);
}
