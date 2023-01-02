package com.example.apispring.service;

import com.example.apispring.dto.response.StudentDto;
import com.example.apispring.dto.StudentInsDto;

import java.util.List;

public interface IStudentService {
    public List<StudentDto> obtenerEstudiantes();
    public boolean agregarEstudiante(StudentDto student);
    public StudentDto buscarEstudiante(int dni);

    public StudentDto eliminarEstudiante(StudentDto studentDto);

    public StudentDto modificarEstudiante(StudentDto studentDto);

    StudentInsDto obtenerStudentIns(int dni);
}
