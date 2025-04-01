package com.application.school.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subject")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "subject")
    private List<ProfessorSubject> professorSubjects;

    @OneToMany(mappedBy = "subject")
    private List<StudentSubject> studentSubjects;

    @OneToMany(mappedBy = "subject")
    private List<SubjectCourse> subjectCourses;
}
