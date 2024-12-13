package com.finance.msfinance.controller.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String identification;
    private String typeIdentification;
    private String name;
    @Email
    private String email;
    private String password;
    private String age;
    @NotNull
    private LocalDate birthday;
    private Character genre;
}
