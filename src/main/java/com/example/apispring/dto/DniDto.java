package com.example.apispring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DniDto {
    @Min(value = 1000000, message = "Ingrese un dni valido")
    @Max(value = 10000000, message = "Ingrese un dni valido")
    private int Dni;
}
