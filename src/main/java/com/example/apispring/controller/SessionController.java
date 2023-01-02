package com.example.apispring.controller;

import com.example.apispring.dto.request.UserRequestDto;
import com.example.apispring.dto.response.UserResponseDto;
import com.example.apispring.service.ISessionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class SessionController {

    private final ISessionService sessionService;

    public SessionController(ISessionService sessionService){
        this.sessionService = sessionService;
    }

    /**
     * Valida usuario y contraseña
     * @param user contiene usuario y contraseña
     * @return UserResponseDto
     */
    @PostMapping("/login")
    public UserResponseDto login(@Valid @RequestBody UserRequestDto user){
        return sessionService.login(user);
    }
}
