package com.finance.msfinance.service;

import com.finance.msfinance.controller.auth.dto.RegisterRequest;
import com.finance.msfinance.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
}
