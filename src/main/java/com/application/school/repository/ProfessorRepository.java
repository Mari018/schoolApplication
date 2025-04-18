package com.application.school.repository;

import com.application.school.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor,Long> {
     Optional<Professor> findByEmail(String email);
}

