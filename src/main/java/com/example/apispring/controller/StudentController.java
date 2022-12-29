package com.example.apispring.controller;

import com.example.apispring.dto.DniDto;
import com.example.apispring.service.IStudentService;
import com.example.apispring.service.StudentService;
import com.example.apispring.dto.StudentDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity<StudentDto> crearEstudiante(@Valid @RequestBody StudentDto studentDto){
        studentService.agregarEstudiante(studentDto);
        return new ResponseEntity<>(studentDto,HttpStatus.OK);
    }
    @GetMapping("/search/{dni}")
    public ResponseEntity<StudentDto> buscarEstudiante(@Valid @PathVariable DniDto dni) {
        StudentDto studentDto = studentService.buscarEstudiante(dni.getDni());
        return new ResponseEntity<>(studentDto, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{dni}")
    public ResponseEntity<StudentDto> eliminarEstudiante(@Valid @PathVariable DniDto dni) {
        StudentDto s = studentService.buscarEstudiante(dni.getDni());
        studentService.eliminarEstudiante(s);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    @PostMapping("/modify")
    public ResponseEntity<StudentDto> modificarEstudiante(@Valid @RequestBody StudentDto studentDto) {
        studentService.modificarEstudiante(studentDto);
        return new ResponseEntity<>(studentDto, HttpStatus.OK);
    }
}
