package com.example.apispring.service;

import com.example.apispring.dto.CourseInsDto;
import com.example.apispring.dto.CourseStudentDto;
import com.example.apispring.entity.CourseStudent;
import com.example.apispring.exception.NotFoundExceptionHandler;
import com.example.apispring.repository.ICourseStudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseStudentService implements ICourseStudentService{

    private ICourseStudentRepository courseStudentRepository;

    public CourseStudentService(ICourseStudentRepository courseStudentRepository){
        this.courseStudentRepository = courseStudentRepository;
    }
    @Override
    public List<CourseStudentDto> obtenerInscripciones(CourseInsDto courseInsDto) throws NotFoundExceptionHandler {
        ObjectMapper mapper = new ObjectMapper();
        Optional<List<CourseStudent>> courseStudents = courseStudentRepository.findByCourseInsId(courseInsDto.getId());
        if (! courseStudents.isPresent()){
            throw new NotFoundExceptionHandler("No se encontraron inscripciones");
        }
        List<CourseStudent> cs = courseStudents.get();
        List<CourseStudentDto> courseStudentDtos = cs
                .stream()
                .map( courseStudent -> {
                    CourseStudentDto courseStudentDto = new CourseStudentDto();
                    courseStudentDto.setCalification(courseStudent.getCalification());
                    courseStudentDto.setYear(courseInsDto.getYear());
                    courseStudentDto.setName(courseInsDto.getName());
                    courseStudentDto.setLastName(courseStudent.getStudentIns().getLastName());
                    courseStudentDto.setFirstName(courseStudent.getStudentIns().getFirstName());
                    courseStudentDto.setDni(courseStudent.getStudentIns().getDni());
                    return courseStudentDto;
                })
                .collect(Collectors.toList());
        return courseStudentDtos;
    }
}
