package com.example.apispring.service;

import com.example.apispring.dto.CourseInsDto;
import com.example.apispring.dto.CourseStudentDto;
import com.example.apispring.exception.NotFoundExceptionHandler;

import java.util.List;

public interface ICourseStudentService {
    List<CourseStudentDto> obtenerInscripciones(CourseInsDto courseInsDto) throws NotFoundExceptionHandler;
}
