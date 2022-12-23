package com.example.apispring.controller;

import com.example.apispring.service.IStudentService;
import com.example.apispring.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    private IStudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping("/getStudents")
    public ResponseEntity<?> obtenerEstudiantes(){
        return new ResponseEntity<>(studentService.obtenerEstudiantes(), HttpStatus.OK);
    }
}
