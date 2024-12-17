package com.finance.msfinance.service;

import com.finance.msfinance.controller.user.dto.UpdateUserRequest;
import com.finance.msfinance.controller.user.dto.UpdateUserResponse;
import com.finance.msfinance.exceptions.UserNotFoundException;
import com.finance.msfinance.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UpdateUserResponse update(UpdateUserRequest request) {
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setModify_date(new Date(System.currentTimeMillis()));
        userRepository.save(user);
        return UpdateUserResponse.builder()
                .message("User updated successfully")
                .timestamp(System.currentTimeMillis())
                .build();
    }
}
