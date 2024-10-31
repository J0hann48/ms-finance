package com.finance.msfinance.controller;

import com.finance.msfinance.controller.request.CreateUserDTO;
import com.finance.msfinance.models.ERol;
import com.finance.msfinance.models.RoleEntity;
import com.finance.msfinance.models.UserEntity;
import com.finance.msfinance.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/index")
    public String index() {
        return "Hola mundo";
    }

    @GetMapping("/index2")
    public String index2() {
        return "Hola mundo not secured";
    }

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO userDTO){
        Set<RoleEntity> roles = userDTO.getRoles()
                .stream()
                .map(role -> RoleEntity.builder()
                        .name(ERol.valueOf(role))
                        .build())
                .collect(Collectors.toSet());

        UserEntity userEntity = UserEntity.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .created_date(new Date(System.currentTimeMillis()))
                .roles(roles)
                .build();
        userRepository.save(userEntity);

        return ResponseEntity.ok(userEntity);
    }

    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestParam String id){
        userRepository.deleteById(Long.parseLong(id));
        return "Se ha borrado el user con id".concat(id);
    }
}

