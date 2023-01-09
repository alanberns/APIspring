package com.example.apispring.utils;

import com.example.apispring.exception.ValueNotValidException;

public class CustomValidation {

    /**
     * Valida el numero de identificacion publico del curso
     * @param numberId numero publico de identificacion del curso
     * @throws "ValueNotValidException"
     */
    public static void validateNumberId(int numberId){
        if (numberId <= 0){
            throw new ValueNotValidException("El numero debe ser mayor a 0");
        }
    }

    /**
     * Valida el dni del estudiante
     * @param dni dni del estudiante
     * @throws "ValueNotValidException"
     */
    public static void validateDni(int dni){
        if (dni < 1000000 || dni > 100000000){
            throw new ValueNotValidException("Ingrese un dni valido");
        }
    }

    /**
     * Valida la calificacion (0-10)
     * @param calification nota del estudiante
     * @throws "ValueNotValidException"
     */
    public static void validateCalification(int calification){
        if (calification < 0 || calification > 10){
            throw new ValueNotValidException("Ingrese una nota entre 0 y 10");
        }
    }
}
