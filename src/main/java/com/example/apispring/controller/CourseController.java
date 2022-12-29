package com.example.apispring.controller;

import com.example.apispring.dto.*;
import com.example.apispring.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/courses")
public class CourseController {

    private ICourseService courseService;
    private ICourseStudentService courseStudentService;
    private IStudentService studentService;

    public CourseController(CourseService courseService,
                            CourseStudentService courseStudentService,
                            StudentService studentService){
        this.courseStudentService = courseStudentService;
        this.courseService = courseService;
        this.studentService = studentService;
    }

    @GetMapping("/getCourses")
    public ResponseEntity<?> obtenerCursos(){
        return new ResponseEntity<>(courseService.obtenerCursos(), HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<CourseDto> crearCurso(@Valid @RequestBody CourseDto courseDto){
        courseService.crearCurso(courseDto);
        return new ResponseEntity<>(courseDto,HttpStatus.OK);
    }
    @PostMapping("/modify")
    public ResponseEntity<CourseDto> modificarCurso(@Valid @RequestBody CourseDto courseDto){
        courseService.mofificarCurso(courseDto);
        return new ResponseEntity<>(courseDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{numberId}")
    public ResponseEntity<CourseDto> eliminarCurso(@Valid @PathVariable NumberIdDto numberId){
        CourseDto courseDto = courseService.buscarCurso(numberId.getNumberId());
        courseService.eliminarCurso(courseDto);
        return new ResponseEntity<>(courseDto, HttpStatus.OK);
    }

    @GetMapping("/search/{numberId}")
    public ResponseEntity<CourseDto> buscarCurso(@Valid @PathVariable NumberIdDto numberId){
        CourseDto courseDto = courseService.buscarCurso(numberId.getNumberId());
        return new ResponseEntity<>(courseDto, HttpStatus.OK);
    }

    @GetMapping("/inscriptions/{numberId}")
    public ResponseEntity<List<CourseStudentDto>> obtenerInscriptos(@Valid @PathVariable NumberIdDto numberId){
        //pedir id del curso a service curso
        CourseInsDto courseInsDto = courseService.obtenerCourseIns(numberId.getNumberId());
        //pedir inscripciones a service coursestudent
        List<CourseStudentDto> courseStudentDtos = courseStudentService.obtenerInscripciones(courseInsDto);
        return new ResponseEntity<>(courseStudentDtos,HttpStatus.OK);
    }

    @PostMapping("/enroll")
    public ResponseEntity<CourseStudentDto> inscribirEstudiante(@Valid @RequestBody DniDto dni, NumberIdDto numberId){

        //pedir id del curso a servicecurso
        CourseInsDto courseInsDto = courseService.obtenerCourseIns(numberId.getNumberId());
        //pedir id del estudiante a servicestudent
        StudentInsDto studentInsDto = studentService.obtenerStudentIns(dni.getDni());
        //crear coursestudent
        courseStudentService.agregarInscripcion(courseInsDto.getId(),studentInsDto.getId());
        CourseStudentDto courseStudentDto = new CourseStudentDto();
        courseStudentDto.setDni(dni.getDni());
        courseStudentDto.setYear(courseInsDto.getYear());
        courseStudentDto.setName(courseInsDto.getName());
        courseStudentDto.setLastName(studentInsDto.getLastName());
        courseStudentDto.setFirstName(studentInsDto.getFirstName());
        return new ResponseEntity<>(courseStudentDto,HttpStatus.OK);
    }


    //eliminar inscripcion
    //poner nota
}
