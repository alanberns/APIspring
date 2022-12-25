package com.example.apispring.service;

import com.example.apispring.dto.CourseDto;

import java.util.List;

public interface ICourseService {
    public List<CourseDto> obtenerCursos();
    public boolean crearCurso(CourseDto courseDto);
}
