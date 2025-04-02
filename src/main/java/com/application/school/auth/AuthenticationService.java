package com.application.school.auth;

import com.application.school.dto.ProfessorDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    public AuthenticationResponse register(ProfessorDto request) {
        return null;
    }

    public AuthenticationResponse authenticate(AuthenticationResponse request) {
        return null;
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {

    }
}


