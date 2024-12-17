package com.finance.msfinance.controller.user;

import com.finance.msfinance.controller.user.dto.UpdateUserRequest;
import com.finance.msfinance.controller.user.dto.UpdateUserResponse;
import com.finance.msfinance.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PutMapping("/update")
    public ResponseEntity<UpdateUserResponse> update(@RequestBody UpdateUserRequest request) {
        UpdateUserResponse response = userService.update(request);
        return ResponseEntity.ok(response);
    }
}

