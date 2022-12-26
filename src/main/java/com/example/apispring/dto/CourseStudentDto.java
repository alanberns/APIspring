package com.example.apispring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseStudentDto {
    private String firstName;
    private String lastName;
    private int dni;
    private String name;
    private int year;
    private int calification;
}
