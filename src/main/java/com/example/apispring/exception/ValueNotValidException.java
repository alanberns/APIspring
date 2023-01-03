package com.example.apispring.exception;

public class ValueNotValidException extends RuntimeException{
    public ValueNotValidException(String message){ super(message); }
}
