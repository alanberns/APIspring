package com.example.apispring.controller;

import com.example.apispring.exception.NotFoundExceptionHandler;
import com.example.apispring.service.IStudentService;
import com.example.apispring.service.StudentService;
import com.example.apispring.dto.StudentDto;
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
    public ResponseEntity<StudentDto> crearEstudiante(@RequestBody StudentDto studentDto){
        //Validar parametros.
        studentService.agregarEstudiante(studentDto);
        return new ResponseEntity<>(studentDto,HttpStatus.OK);
    }
    @GetMapping("/search/{dni}")
    public ResponseEntity<StudentDto> buscarEstudiante(@PathVariable int dni) throws NotFoundExceptionHandler {
        //validar dni
        StudentDto studentDto = studentService.buscarEstudiante(dni);
        return new ResponseEntity<>(studentDto, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{dni}")
    public ResponseEntity<StudentDto> eliminarEstudiante(@PathVariable int dni) throws NotFoundExceptionHandler {
        //validar dni
        StudentDto s = studentService.buscarEstudiante(dni);
        studentService.eliminarEstudiante(s);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    @PostMapping("/modify")
    public ResponseEntity<StudentDto> modificarEstudiante(@RequestBody StudentDto studentDto) throws NotFoundExceptionHandler {
        //validar datos
        studentService.modificarEstudiante(studentDto);
        return new ResponseEntity<>(studentDto, HttpStatus.OK);
    }
}
