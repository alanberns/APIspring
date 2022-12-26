package com.example.apispring.service;

import com.example.apispring.dto.CourseDto;
import com.example.apispring.dto.CourseInsDto;
import com.example.apispring.entity.Course;
import com.example.apispring.exception.NotFoundExceptionHandler;
import com.example.apispring.repository.ICourseRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
                    return new CourseDto(course.getName(),course.getYear(),course.isActive(),course.getNumberId());
                })
                .collect(Collectors.toList());
        return coursesDto;
    }

    @Override
    public boolean crearCurso(CourseDto courseDto) {
        ObjectMapper mapper = new ObjectMapper();
        Course c = mapper.convertValue(courseDto, Course.class);
        courseRepository.save(c);
        return true;
    }

    @Override
    public CourseDto mofificarCurso(CourseDto courseDto) throws NotFoundExceptionHandler {
        Optional<Course> course = courseRepository.findCourseByNumberId(courseDto.getNumberId());
        if (! course.isPresent()){
            throw new NotFoundExceptionHandler("No se encontro un curso");
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        Course c = course.get();
        c.setActive(courseDto.isActive());
        c.setName(courseDto.getName());
        c.setYear(courseDto.getYear());
        c.setNumberId(courseDto.getNumberId());
        courseRepository.save(c);
        CourseDto cDto = mapper.convertValue(c,CourseDto.class);
        return cDto;
    }

    @Override
    public CourseDto eliminarCurso(CourseDto courseDto) throws NotFoundExceptionHandler {
        courseDto.setActive(false);
        mofificarCurso((courseDto));
        return courseDto;
    }

    @Override
    public CourseDto buscarCurso(int numberId) throws NotFoundExceptionHandler {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Optional<Course> course = courseRepository.findCourseByNumberId(numberId);
        if (course.isPresent()){
            throw new NotFoundExceptionHandler("No se encontro un curso");
        }
        Course c = course.get();
        CourseDto courseDto = mapper.convertValue(c, CourseDto.class);
        return courseDto;
    }

    @Override
    public CourseInsDto obtenerCourseIns(int numberId) throws NotFoundExceptionHandler {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Optional<Course> optionalCourse = courseRepository.findCourseByNumberId(numberId);
        if (! optionalCourse.isPresent()){
            throw new NotFoundExceptionHandler("No se encontro el curso");
        }
        Course course = optionalCourse.get();
        CourseInsDto courseInsDto = mapper.convertValue(course,CourseInsDto.class);
        return courseInsDto;
    }
}
