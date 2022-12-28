package com.example.apispring.service;

import com.example.apispring.dto.CourseInsDto;
import com.example.apispring.dto.CourseStudentDto;

import java.util.List;

public interface ICourseStudentService {
    List<CourseStudentDto> obtenerInscripciones(CourseInsDto courseInsDto);

    void agregarInscripcion(Long id_course, Long id_student);
}
