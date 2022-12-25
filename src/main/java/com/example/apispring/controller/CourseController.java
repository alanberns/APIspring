package com.example.apispring.controller;

import com.example.apispring.dto.CourseDto;
import com.example.apispring.dto.StudentDto;
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
        return new ResponseEntity<CourseDto>(courseDto,HttpStatus.OK);
    }
}
