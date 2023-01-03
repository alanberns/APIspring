package com.example.apispring.service;

import com.example.apispring.dto.response.CourseDto;
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

    /**
     * Retorna todos los cursos
     * @return lista de cursos
     */
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

    /**
     * Crea un nuevo curso
     * @param courseDto datos del curso
     * @return devuelve true
     */
    @Override
    public boolean crearCurso(CourseDto courseDto){
        ObjectMapper mapper = new ObjectMapper();
        Course c = mapper.convertValue(courseDto, Course.class);
        courseRepository.save(c);
        return true;
    }

    /**
     * Modifica un curso existente
     * @param courseDto recibe la informacion del curso
     * @return la informacion del curso actualizada
     */
    @Override
    public CourseDto modificarCurso(CourseDto courseDto){
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

    /**
     * Eliminar un curso
     * @param numberId Recibe el numero publico de identificacion del curso
     * @return retorna el curso eliminado
     */
    @Override
    public CourseDto eliminarCurso(CourseDto courseDto){
        courseDto.setActive(false);
        modificarCurso((courseDto));
        return courseDto;
    }

    /**
     * Busca un curso
     * @param numberId recibe el numero publico de identificacion del curso
     * @return Retorna el curso
     */
    @Override
    public CourseDto buscarCurso(int numberId){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Optional<Course> course = courseRepository.findCourseByNumberId(numberId);
        if (course.isPresent()){
            throw new NotFoundExceptionHandler("No se encontro el curso");
        }
        Course c = course.get();
        CourseDto courseDto = mapper.convertValue(c, CourseDto.class);
        return courseDto;
    }

    /**
     * Busca un curso
     * @param numberId recibe el numero publico de identificacion del curso
     * @return Retorna el curso (id privada)
     */
    @Override
    public CourseInsDto obtenerCourseIns(int numberId){
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
