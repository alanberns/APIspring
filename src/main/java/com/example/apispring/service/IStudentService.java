package com.example.apispring.service;

import com.example.apispring.dto.StudentDto;
import com.example.apispring.exception.NotFoundExceptionHandler;

import java.util.List;

public interface IStudentService {
    public List<StudentDto> obtenerEstudiantes();
    public boolean agregarEstudiante(StudentDto student);
    public StudentDto buscarEstudiante(int dni) throws NotFoundExceptionHandler;

    public StudentDto eliminarEstudiante(StudentDto studentDto) throws NotFoundExceptionHandler;

    public StudentDto modificarEstudiante(StudentDto studentDto) throws NotFoundExceptionHandler;
}
