package com.example.apispring.dto.request;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class UserRequestDto {
    @Size(min = 5, max = 30, message = "El nombre de usuario debe tener entre 5 y 30 caracteres")
    private String username;

    @Size(min = 5, max = 30, message = "La contraseña debe tener entre 5 y 30 caracteres")
    private String password;
}
