package com.application.school.repository;

import com.application.school.entity.Professor;
import com.application.school.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Long> {

    Optional<Token> findByToken(String jwt);
}

