package com.example.apispring.service;

import com.example.apispring.dto.CourseDto;
import com.example.apispring.entity.Course;
import com.example.apispring.repository.ICourseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService implements ICourseService{

    private ICourseRepository courseRepository;

    public CourseService(ICourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    @Override
    public List<CourseDto> obtenerCursos(){
        ObjectMapper mapper = new ObjectMapper();
        List<Course>courses = courseRepository.findAll();
        List<CourseDto>coursesDto = courses
                .stream()
                .map( course -> {
                    return new CourseDto(course.getName(),course.getYear(),course.isActive());
                })
                .collect(Collectors.toList());
        return coursesDto;
    }
}
