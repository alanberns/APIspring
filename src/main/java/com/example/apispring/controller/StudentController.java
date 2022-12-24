package com.example.apispring.controller;

import com.example.apispring.dto.CourseDto;
import com.example.apispring.service.IStudentService;
import com.example.apispring.service.StudentService;
import com.example.apispring.entity.Student;
import com.example.apispring.dto.StudentDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    private IStudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping("/getStudents")
    public ResponseEntity<?> obtenerEstudiantes(){
        return new ResponseEntity<>(studentService.obtenerEstudiantes(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<StudentDto> crearEstudiante(@RequestBody StudentDto student){
        //Validar parametros.
        studentService.agregarEstudiante(student);
        return new ResponseEntity<StudentDto>(student,HttpStatus.OK);
    }

    //@DeleteMapping
    //@GetMapping
}
