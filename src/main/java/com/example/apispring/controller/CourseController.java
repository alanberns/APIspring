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

    /**
     * @return devuelve la lista de cursos
     */
    @GetMapping("/getCourses")
    public ResponseEntity<?> obtenerCursos(){
        return new ResponseEntity<>(courseService.obtenerCursos(), HttpStatus.OK);
    }

    /**
     * Crea un nuevo curso
     * @param courseDto datos del curso
     * @return devuelve la informacion del curso cuando es agregado
     */
    @PostMapping("/create")
    public ResponseEntity<CourseDto> crearCurso(@Valid @RequestBody CourseDto courseDto){
        courseService.crearCurso(courseDto);
        return new ResponseEntity<>(courseDto,HttpStatus.OK);
    }

    /**
     * Modifica un curso existente
     * @param courseDto recibe la informacion del curso
     * @return la informacion del curso actualizada
     */
    @PostMapping("/modify")
    public ResponseEntity<CourseDto> modificarCurso(@Valid @RequestBody CourseDto courseDto){
        courseService.modificarCurso(courseDto);
        return new ResponseEntity<>(courseDto, HttpStatus.OK);
    }

    /**
     * Eliminar un curso
     * @param numberId Recibe el numero publico de identificacion del curso
     * @return retorna el curso eliminado
     */
    @DeleteMapping("/delete/{numberId}")
    public ResponseEntity<CourseDto> eliminarCurso(@Valid @PathVariable NumberIdDto numberId){
        CourseDto courseDto = courseService.buscarCurso(numberId.getNumberId());
        courseService.eliminarCurso(courseDto);
        return new ResponseEntity<>(courseDto, HttpStatus.OK);
    }

    /**
     * Busca un curso
     * @param numberId recibe el numero publico de identificacion del curso
     * @return Retorna el curso
     */
    @GetMapping("/search/{numberId}")
    public ResponseEntity<CourseDto> buscarCurso(@Valid @PathVariable NumberIdDto numberId){
        CourseDto courseDto = courseService.buscarCurso(numberId.getNumberId());
        return new ResponseEntity<>(courseDto, HttpStatus.OK);
    }

    /**
     * Busca los estudiantes inscriptos a un curso
     * @param numberId recibe el numero publico de identificacion del curso
     * @return Retorna una lista con los datos de la inscripcion (estudiante y curso)
     */
    @GetMapping("/inscriptions/{numberId}")
    public ResponseEntity<List<CourseStudentDto>> obtenerInscriptos(@Valid @PathVariable NumberIdDto numberId){
        //pedir id del curso a service curso
        CourseInsDto courseInsDto = courseService.obtenerCourseIns(numberId.getNumberId());
        //pedir inscripciones a service coursestudent
        List<CourseStudentDto> courseStudentDtos = courseStudentService.obtenerInscripciones(courseInsDto);
        return new ResponseEntity<>(courseStudentDtos,HttpStatus.OK);
    }

    /**
     * Inscribir un estudiante a un curso
     * @param numberId numero publico de identificacion del curso
     * @param dni numero de dni del estudiante
     * @return Datos de la inscripcion (estudiante y curso)
     */
    @PostMapping("/enroll")
    public ResponseEntity<CourseStudentDto> inscribirEstudiante(@Valid @RequestBody NumberIdDto numberId, DniDto dni){

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


    /**
     * Elimina la inscripcion de un estudiante en un curso
     * @param numberId numero publico de identificacion del curso
     * @param dni numero de dni del estudiante
     * @return  "inscripcion eliminada"
     */
    //eliminar inscripcion

    /**
     * Añadir la nota del estudiante en el curso
     * @param numberId numero publico de identificacion del curso
     * @param dni numero de dni del estudiante
     * @param nota nota del estudiante en el curso
     * @return
     */
    //poner nota
}
