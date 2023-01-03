package com.example.apispring.controller;

import com.example.apispring.dto.response.StudentDto;
import com.example.apispring.service.IStudentService;
import com.example.apispring.service.StudentService;
import com.example.apispring.utils.CustomValidation;
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

    /**
     * Obtener la lista de estudiantes
     * @return lista de estudiantes
     */
    @GetMapping("/getStudents")
    public ResponseEntity<?> obtenerEstudiantes(){
        return new ResponseEntity<>(studentService.obtenerEstudiantes(), HttpStatus.OK);
    }

    /**
     * Crear un nuevo esudiante
     * @param studentDto datos del estudiante
     * @return la informacion del estudiante nuevo
     */
    @PostMapping("/create")
    public ResponseEntity<StudentDto> crearEstudiante(@Valid @RequestBody StudentDto studentDto){
        studentService.agregarEstudiante(studentDto);
        return new ResponseEntity<>(studentDto,HttpStatus.OK);
    }

    /**
     * Buscar un estudiante por dni
     * @param dni dni del estudiante
     * @return datos del estudiante
     */
    @GetMapping("/search/{dni}")
    public ResponseEntity<StudentDto> buscarEstudiante(@PathVariable int dni) {
        CustomValidation.validateDni(dni);
        StudentDto studentDto = studentService.buscarEstudiante(dni);
        return new ResponseEntity<>(studentDto, HttpStatus.OK);
    }

    /**
     * Eliminar un estudiante. Eliminacion logica
     * @param dni dni del estudiante
     * @return retorna los datos del estudiante
     */
    @DeleteMapping("/delete/{dni}")
    public ResponseEntity<StudentDto> eliminarEstudiante(@PathVariable int dni) {
        CustomValidation.validateDni(dni);
        StudentDto s = studentService.buscarEstudiante(dni);
        studentService.eliminarEstudiante(s);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    /**
     * Modificar estudiante
     * @param studentDto datos del estudiante
     * @return los datos del estudiante
     */
    @PostMapping("/modify")
    public ResponseEntity<StudentDto> modificarEstudiante(@Valid @RequestBody StudentDto studentDto) {
        studentService.modificarEstudiante(studentDto);
        return new ResponseEntity<>(studentDto, HttpStatus.OK);
    }

    /**
     * Mostrar los cursos a los que esta inscripto un estudiante
     * @param dni dni del estudiante
     * @return lista de cursos del estudiante
     */
    //inscripciones
}
