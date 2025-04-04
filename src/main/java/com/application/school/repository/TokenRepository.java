package com.application.school.repository;

import com.application.school.entity.Professor;
import com.application.school.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Long> {

    Optional<Token> findByToken(String jwt);

    @Query(value = "SELECT t.* FROM token t " +
            "INNER JOIN \"user\" u ON t.user_id = u.id " +
            "WHERE u.id = :id AND (t.expired = false OR t.revoked = false)",
            nativeQuery = true)
    List<Token> findAllValidTokenByUser(Long id);
}

