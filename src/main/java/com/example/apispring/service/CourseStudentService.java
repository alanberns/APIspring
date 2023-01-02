package com.example.apispring.service;

import com.example.apispring.dto.CourseInsDto;
import com.example.apispring.dto.response.CourseStudentDto;
import com.example.apispring.entity.Student;
import com.example.apispring.entity.Course;
import com.example.apispring.entity.CourseStudent;
import com.example.apispring.exception.NotFoundExceptionHandler;
import com.example.apispring.repository.ICourseRepository;
import com.example.apispring.repository.ICourseStudentRepository;
import com.example.apispring.repository.IStudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseStudentService implements ICourseStudentService{

    private ICourseStudentRepository courseStudentRepository;
    private ICourseRepository courseRepository;
    private IStudentRepository studentRepository;

    public CourseStudentService(ICourseStudentRepository courseStudentRepository,
                                ICourseRepository courseRepository,
                                IStudentRepository studentRepository){
        this.courseStudentRepository = courseStudentRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    /**
     * Obtener estudiantes inscriptos a un curso
     * @param courseInsDto datos del curso (id privada, nombre, año)
     * @return datos de las inscripciones al curso
     */
    @Override
    public List<CourseStudentDto> obtenerInscripciones(CourseInsDto courseInsDto){
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

    /**
     * Inscribir a un estudiante a un curso
     * @param id_course id privada del curso
     * @param id_student id privada del estudiante
     * @return true
     */
    @Override
    public boolean agregarInscripcion(Long id_course, Long id_student){
        Optional<Course> course = courseRepository.findById(id_course);
        Optional<Student> student = studentRepository.findById(id_student);
        CourseStudent courseStudent = new CourseStudent();
        if (! course.isPresent()){
            throw new NotFoundExceptionHandler("No se encontro el curso");
        }
        if (! student.isPresent()){
            throw new NotFoundExceptionHandler("No se encontro el estudiante");
        }
        Course c = course.get();
        Student s = student.get();
        courseStudent.setCourseIns(c);
        courseStudent.setStudentIns(s);
        courseStudentRepository.save(courseStudent);
        return true;
    }
}
