package com.example.apispring;

import com.example.apispring.exception.ValueNotValidException;
import com.example.apispring.utils.CustomValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomValidationTest {


    @Test
    void validateNumberIdTest(){
        //arrange
        int numberIdOk = 1;
        //act
        CustomValidation.validateNumberId(numberIdOk);

        //arrange
        int numberIdError = 0;
        //act
        //assert
        Assertions.assertThrows(
                ValueNotValidException.class,
                ()-> CustomValidation.validateNumberId(numberIdError),
                "El numero debe ser mayor a 0"
        );
    }

    @Test
    void validateDniTest(){
        //arrange
        int dniValid = 41892020;
        int dniInvalid = 999999;
        int dniInvalidMax = 100000001;
        //act
        CustomValidation.validateDni(dniValid);
        //assert
        Assertions.assertThrows(
                ValueNotValidException.class,
                ()-> CustomValidation.validateDni(dniInvalid),
                "Ingrese un dni valido"
        );
        Assertions.assertThrows(
                ValueNotValidException.class,
                ()->CustomValidation.validateDni(dniInvalidMax),
                "Ingrese un dni valido"
        );
    }

    @Test
    void validateCalificationTest(){
        //arrange
        int nota = 10;
        int notaMenor = -1;
        int notaMayor = 11;
        //act
        CustomValidation.validateCalification(nota);
        //assert
        Assertions.assertThrows(
                ValueNotValidException.class,
                ()->CustomValidation.validateCalification(notaMenor),
                "Ingrese una nota entre 0 y 10"
        );
        Assertions.assertThrows(
                ValueNotValidException.class,
                ()->CustomValidation.validateCalification(notaMayor),
                "Ingrese una nota entre 0 y 10"
        );
    }
}
