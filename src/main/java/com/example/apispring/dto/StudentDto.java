package com.example.apispring.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentDto {

    private String firstName;
    private String lastName;
    private int dni;
    private boolean active;
}
