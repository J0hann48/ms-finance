package com.finance.msfinance.controller.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "The email must be in a valid format"
    )
    private String email;
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,20}$",
            message = "La contraseña debe tener entre 8 y 20 caracteres, incluyendo al menos un número, una letra minúscula, una letra mayúscula y un carácter especial"
    )
    private String password;
    private String age;
    @NotNull
    private LocalDate birthday;
    private Character genre;
}
