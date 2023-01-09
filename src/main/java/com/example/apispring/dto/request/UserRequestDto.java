package com.example.apispring.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    @Size(min = 5, max = 30, message = "El nombre de usuario debe tener entre 5 y 30 caracteres")
    private String username;

    @Size(min = 5, max = 30, message = "La contraseña debe tener entre 5 y 30 caracteres")
    private String password;
}
