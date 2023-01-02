package com.example.apispring.exception;

import com.example.apispring.dto.response.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;


import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionConfig {

    @ExceptionHandler(NotFoundExceptionHandler.class)
    public ResponseEntity<?>notFoundException(NotFoundExceptionHandler e){
        ErrorDto errorDto = new ErrorDto(404,e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validacionErronea(MethodArgumentNotValidException ex){

        List<ErrorDto> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new ErrorDto(400,error.getDefaultMessage()))
                .distinct()
                .collect(Collectors.toList());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
