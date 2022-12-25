package com.example.apispring.service;

import com.example.apispring.dto.StudentDto;

import java.util.List;

public interface IStudentService {
    public List<StudentDto> obtenerEstudiantes();
    public boolean agregarEstudiante(StudentDto student);
    public StudentDto buscarEstudiante(int dni);

    StudentDto eliminarEstudiante(StudentDto studentDto);

    StudentDto modificarEstudiante(StudentDto studentDto);
}
