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
public class CalificationDto {
    @Min(value = 0, message = "Ingrese una nota entre 0 y 10")
    @Max(value = 10, message = "Ingrese una nota entre 0 y 10")
    private int calification;
}
