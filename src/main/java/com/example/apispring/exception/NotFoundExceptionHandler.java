package com.example.apispring.exception;

public class NotFoundExceptionHandler extends Exception{
    public NotFoundExceptionHandler(String mensaje) {
        super(mensaje);
    }
}
