package com.finance.msfinance.controller;

import com.finance.msfinance.controller.auth.dto.RegisterRequest;
import com.finance.msfinance.repositories.UserRepository;
import jakarta.validation.Valid;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1")
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/index")
    public String index() {
        return "Hola mundo";
    }

    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestParam String id){
        userRepository.deleteById(Long.parseLong(id));
        return "Se ha borrado el user con id".concat(id);
    }
}

