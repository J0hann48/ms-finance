package com.finance.msfinance.service;

import com.finance.msfinance.config.jwt.JwtService;
import com.finance.msfinance.controller.auth.dto.AuthenticationRequest;
import com.finance.msfinance.controller.auth.dto.AuthenticationResponse;
import com.finance.msfinance.controller.auth.dto.RegisterRequest;
import com.finance.msfinance.models.ERol;
import com.finance.msfinance.models.UserEntity;
import com.finance.msfinance.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public AuthenticationResponse register(RegisterRequest request) {
        var user = UserEntity.builder()
                .identification(request.getIdentification())
                .typeIdentification(request.getTypeIdentification())
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .age(request.getAge())
                .birthday(request.getBirthday())
                .genre(request.getGenre())
                .created_date(new Date(System.currentTimeMillis()))
                .role(ERol.USER)
                .build();
        logger.info("User register {}", user.toString());
        userRepository.save(user);
        logger.info("User save in DB");

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        logger.info("User authenticate {}", user.getUsername());
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
