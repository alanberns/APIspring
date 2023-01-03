package com.example.apispring.service;

import com.example.apispring.dto.CourseInsDto;
import com.example.apispring.dto.StudentInsDto;
import com.example.apispring.dto.response.CourseStudentDto;

import java.util.List;

public interface ICourseStudentService {
    List<CourseStudentDto> obtenerInscripciones(CourseInsDto courseInsDto);

    boolean agregarInscripcion(Long id_course, Long id_student);

    boolean agregarNota(Long id_course, Long id_student, int calification);

    boolean eliminarInscripcion(Long id_course, Long id_student);

    List<CourseStudentDto> obtenerInscripciones(StudentInsDto studentInsDto);

    void estaInscripto(Long id_course, Long id_student);
}
