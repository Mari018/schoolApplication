package com.application.school.dto;

import com.application.school.entity.Course;
import com.application.school.entity.Subject;
import com.application.school.enums.GenderType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public class ProfessorDto {
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
    @NotBlank(message = "Student must have a subject")
    private Subject subject;
}
