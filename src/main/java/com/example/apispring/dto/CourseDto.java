package com.example.apispring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseDto {
    @Size(max = 60, message = "El tamaño maximo del nombre es de 60 caracteres")
    @NotBlank(message = "El nombre debe completarse")
    private String name;
    @Min(value = 1990, message = "Ingrese un año mayor a 1990")
    @Max(value = 2200, message = "Ingrese un año menor a 2200")
    private int year;
    @NotEmpty(message = "True/False")
    private boolean active;
    @PositiveOrZero(message = "Ingrese un numero positivo")
    private int numberId;
}
