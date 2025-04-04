package com.application.school.auth;

import com.application.school.config.JwtService;
import com.application.school.dto.ProfessorDto;
import com.application.school.dto.ProfessorRequestDto;
import com.application.school.entity.Professor;
import com.application.school.entity.Token;
import com.application.school.enums.TokenType;
import com.application.school.exception.ProfessorNotFoundException;
import com.application.school.repository.ProfessorRepository;
import com.application.school.repository.TokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
public class AuthenticationService {

    private final ProfessorRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(ProfessorRepository repository, TokenRepository tokenRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(ProfessorDto request) {
        Professor professor = Professor.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .birthday(request.getBirthday())
                .gender(request.getGender())
                .build();

        UserDetails userDetails = new User(
                professor.getEmail(),
                professor.getPassword(),
                Collections.emptyList());

        repository.save(professor);
        String jwtToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(ProfessorRequestDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        Professor professor = repository.findByEmail(request.getEmail()).orElseThrow(()-> new ProfessorNotFoundException("this professor do not exist"));
        UserDetails userDetails = new User(
                professor.getEmail(),
                professor.getPassword(),
                Collections.emptyList());

        String jwtToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);
        revokeAllUserTokens(professor);
        saveUserToken(professor, jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void saveUserToken(Professor professor, String jwtToken){
        Token token = Token.builder()
                .professor(professor)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String professorEmail;

        if(authHeader != null || !authHeader.startsWith("Bearer ")){
            return;
        }


        refreshToken = authHeader.substring(7);
        professorEmail = jwtService.extractUsername(refreshToken);

        if (professorEmail != null) {
           Professor professor = repository.findByEmail(professorEmail).orElseThrow();
            UserDetails userDetails = new User(
                    professor.getEmail(),
                    professor.getPassword(),
                    Collections.emptyList()
            );
            if (jwtService.isTokenValid(refreshToken,userDetails)){
                String accessToken = jwtService.generateToken(userDetails);
                revokeAllUserTokens(professor);
                saveUserToken(professor,accessToken);
                AuthenticationResponse authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();

                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }

    }

    private void revokeAllUserTokens(Professor professor) {
        List<Token> validToken = tokenRepository.findAllValidTokenByUser(professor.getId());

        if(validToken.isEmpty()){
            return;
        }

        validToken.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validToken);

    }
}


