package com.example.apispring.service;

import com.example.apispring.dto.CourseDto;
import com.example.apispring.dto.CourseInsDto;
import com.example.apispring.exception.NotFoundExceptionHandler;

import java.util.List;

public interface ICourseService {
    public List<CourseDto> obtenerCursos();
    public boolean crearCurso(CourseDto courseDto);

    public CourseDto modificarCurso(CourseDto courseDto) throws NotFoundExceptionHandler;

    public CourseDto eliminarCurso(CourseDto courseDto) throws NotFoundExceptionHandler;

    public CourseDto buscarCurso(int numberId) throws NotFoundExceptionHandler;

    CourseInsDto obtenerCourseIns(int numberId) throws NotFoundExceptionHandler;
}
