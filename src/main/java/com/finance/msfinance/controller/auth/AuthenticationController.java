package com.finance.msfinance.controller.auth;

import com.finance.msfinance.controller.auth.dto.AuthenticationRequest;
import com.finance.msfinance.controller.auth.dto.AuthenticationResponse;
import com.finance.msfinance.controller.auth.dto.RegisterRequest;
import com.finance.msfinance.service.AuthenticationService;
import com.finance.msfinance.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> generateToken(@Valid @RequestBody RegisterRequest request){
        logger.info("Authentication request received for register user: {}", request.getEmail());
        AuthenticationResponse tokenResponse = authenticationService.register(request);
        if (tokenResponse != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(tokenResponse);
        }
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        logger.info("Authentication request received for authenticated user: {}", request.getEmail());
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
