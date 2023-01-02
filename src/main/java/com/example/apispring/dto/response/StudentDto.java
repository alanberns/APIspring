package com.example.apispring.dto.response;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentDto {

    @Size(min = 1,max = 50,message = "Ingrese un nombre, con maximo 50 caracteres")
    private String firstName;
    @Size(min = 1,max = 50,message = "Ingrese un apellido, con maximo 50 caracteres")
    private String lastName;
    @Min(value = 1000000, message = "Ingrese un dni valido")
    @Max(value = 10000000, message = "Ingrese un dni valido")
    private int dni;
    @NotEmpty(message = "True/False")
    private boolean active;
}
