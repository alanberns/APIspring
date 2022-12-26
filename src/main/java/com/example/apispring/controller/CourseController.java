package com.example.apispring.controller;

import com.example.apispring.dto.CourseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.apispring.service.ICourseService;
import com.example.apispring.service.CourseService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private ICourseService courseService;

    public CourseController(CourseService courseService){
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
    public ResponseEntity<CourseDto> modificarCurso(@RequestBody CourseDto courseDto){
        //validar datos
        courseService.mofificarCurso(courseDto);
        return new ResponseEntity<>(courseDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{numberId}")
    public ResponseEntity<CourseDto> eliminarCurso(@PathVariable int numberId){
        //validar name
        CourseDto courseDto = courseService.buscarCurso(numberId);
        courseService.eliminarCurso(courseDto);
        return new ResponseEntity<>(courseDto, HttpStatus.OK);
    }

    @GetMapping("/search/{numberId}")
    public ResponseEntity<CourseDto> buscarCurso(@PathVariable int numberId){
        CourseDto courseDto = courseService.buscarCurso(numberId);
        return new ResponseEntity<>(courseDto, HttpStatus.OK);
    }
}
