package com.example.apispring.exception;

public class NotFoundExceptionHandler extends RuntimeException{
    public NotFoundExceptionHandler(String mensaje) {
        super(mensaje);
    }
}
