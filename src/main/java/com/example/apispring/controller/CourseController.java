package com.example.apispring.controller;

import com.example.apispring.dto.CourseDto;
import com.example.apispring.dto.CourseInsDto;
import com.example.apispring.dto.CourseStudentDto;
import com.example.apispring.exception.NotFoundExceptionHandler;
import com.example.apispring.service.CourseStudentService;
import com.example.apispring.service.ICourseStudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.apispring.service.ICourseService;
import com.example.apispring.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private ICourseService courseService;
    private ICourseStudentService courseStudentService;

    public CourseController(CourseService courseService, CourseStudentService courseStudentService){
        this.courseStudentService = courseStudentService;
        this.courseService = courseService;
    }

    @GetMapping("/getCourses")
    public ResponseEntity<?> obtenerCursos(){
        return new ResponseEntity<>(courseService.obtenerCursos(), HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<CourseDto> crearCurso(@RequestBody CourseDto courseDto){
        //Validar parametros.
        courseService.crearCurso(courseDto);
        return new ResponseEntity<>(courseDto,HttpStatus.OK);
    }
    @PostMapping("/modify")
    public ResponseEntity<CourseDto> modificarCurso(@RequestBody CourseDto courseDto) throws NotFoundExceptionHandler {
        //validar datos
        courseService.mofificarCurso(courseDto);
        return new ResponseEntity<>(courseDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{numberId}")
    public ResponseEntity<CourseDto> eliminarCurso(@PathVariable int numberId) throws NotFoundExceptionHandler {
        //validar name
        CourseDto courseDto = courseService.buscarCurso(numberId);
        courseService.eliminarCurso(courseDto);
        return new ResponseEntity<>(courseDto, HttpStatus.OK);
    }

    @GetMapping("/search/{numberId}")
    public ResponseEntity<CourseDto> buscarCurso(@PathVariable int numberId) throws NotFoundExceptionHandler {
        CourseDto courseDto = courseService.buscarCurso(numberId);
        return new ResponseEntity<>(courseDto, HttpStatus.OK);
    }

    @GetMapping("/inscriptions/{numberId}")
    public ResponseEntity<List<CourseStudentDto>> obtenerInscriptos(@PathVariable int numberId)throws NotFoundExceptionHandler {
        //pedir id del curso a service curso
        CourseInsDto courseInsDto = courseService.obtenerCourseIns(numberId);
        //pedir inscripciones a service coursestudent
        List<CourseStudentDto> courseStudentDtos = courseStudentService.obtenerInscripciones(courseInsDto);
        return new ResponseEntity<>(courseStudentDtos,HttpStatus.OK);
    }
}
