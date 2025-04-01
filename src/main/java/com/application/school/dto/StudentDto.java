package com.application.school.dto;

import com.application.school.entity.Course;
import com.application.school.enums.CourseType;
import com.application.school.enums.GenderType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class StudentDto {
    @NotBlank(message = "This must have a first name")
    private String firstName;
    @NotBlank(message = "Student must have a last name")
    private String lastName;
    @NotBlank(message = "Missing gender")
    private GenderType gender;
    private Date birthday;
    @Email()
    @NotBlank(message = "Fill the email")
    private String email;
    @NotBlank(message = "Student must have a course")
    private Course course;

}
