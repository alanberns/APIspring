package com.example.apispring.service;

import com.example.apispring.dto.request.UserRequestDto;
import com.example.apispring.dto.response.UserResponseDto;

public interface ISessionService {
    /**
     * Valida el usuario y contraseña ingresados
     * Si los datos son correctos, devuelve la cuenta con el token necesario
     * para realizar las consultas
     *
     * @param user contiene username y password
     * @return UserResponseDto contiene username y token
     * @throws "NotFoundExceptionHandler" si no se encuentra al usuario
     */
    UserResponseDto login(UserRequestDto user);
}
