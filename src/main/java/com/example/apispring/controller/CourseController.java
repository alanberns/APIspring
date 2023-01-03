package com.example.apispring.controller;

import com.example.apispring.dto.CourseInsDto;
import com.example.apispring.dto.StudentInsDto;
import com.example.apispring.dto.request.CalificationDto;
import com.example.apispring.dto.request.CourseStudentReqDto;
import com.example.apispring.dto.response.CourseDto;
import com.example.apispring.dto.response.CourseStudentDto;
import com.example.apispring.service.*;
import com.example.apispring.utils.CustomValidation;
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
    public ResponseEntity<CourseDto> eliminarCurso(@PathVariable int numberId){
        CustomValidation.validateNumberId(numberId);
        CourseDto courseDto = courseService.buscarCurso(numberId);
        courseService.eliminarCurso(courseDto);
        return new ResponseEntity<>(courseDto, HttpStatus.OK);
    }


    /**
     * Busca un curso
     * @param numberId recibe el numero publico de identificacion del curso
     * @return Retorna el curso
     */
    @GetMapping("/search/{numberId}")
    public ResponseEntity<CourseDto> buscarCurso(@PathVariable int numberId){
        CustomValidation.validateNumberId(numberId);
        CourseDto courseDto = courseService.buscarCurso(numberId);
        return new ResponseEntity<>(courseDto, HttpStatus.OK);
    }

    /**
     * Busca los estudiantes inscriptos a un curso
     * @param numberId recibe el numero publico de identificacion del curso
     * @return Retorna una lista con los datos de la inscripcion (estudiante y curso)
     */
    @GetMapping("/inscriptions/{numberId}")
    public ResponseEntity<List<CourseStudentDto>> obtenerInscriptos(@PathVariable int numberId){
        CustomValidation.validateNumberId(numberId);
        //pedir id del curso a service curso
        CourseInsDto courseInsDto = courseService.obtenerCourseIns(numberId);
        //pedir inscripciones a service coursestudent
        List<CourseStudentDto> courseStudentDtos = courseStudentService.obtenerInscripciones(courseInsDto);
        return new ResponseEntity<>(courseStudentDtos,HttpStatus.OK);
    }

    /**
     * Inscribir un estudiante a un curso
     * @param courseStudentReqDto contiene dni y numberId
     * @return Datos de la inscripcion (estudiante y curso)
     */
    @PostMapping("/enroll")
    public ResponseEntity<CourseStudentDto> inscribirEstudiante(@RequestBody CourseStudentReqDto courseStudentReqDto) {
        //validar datos
        int numberId = courseStudentReqDto.getNumberId();
        int dni = courseStudentReqDto.getDni();
        CustomValidation.validateNumberId(numberId);
        CustomValidation.validateDni(dni);

        //pedir id del curso a servicecurso
        CourseInsDto courseInsDto = courseService.obtenerCourseIns(numberId);
        //pedir id del estudiante a servicestudent
        StudentInsDto studentInsDto = studentService.obtenerStudentIns(dni);

        //Comprobar si el estudiante ya esta inscripto
        courseStudentService.estaInscripto(courseInsDto.getId(),studentInsDto.getId());

        //crear coursestudent
        courseStudentService.agregarInscripcion(courseInsDto.getId(),studentInsDto.getId());
        CourseStudentDto courseStudentDto = new CourseStudentDto();
        courseStudentDto.setDni(dni);
        courseStudentDto.setYear(courseInsDto.getYear());
        courseStudentDto.setName(courseInsDto.getName());
        courseStudentDto.setLastName(studentInsDto.getLastName());
        courseStudentDto.setFirstName(studentInsDto.getFirstName());
        courseStudentDto.setCalification(-1);
        return new ResponseEntity<>(courseStudentDto,HttpStatus.OK);
    }


    /**
     * Elimina la inscripcion de un estudiante en un curso
     * @param courseStudentReqDto contiene dni y numberId del curso
     * @return  "inscripcion eliminada"
     */
    @PostMapping("/inscriptions/delete")
    public ResponseEntity<String> eliminarInscripcion(@RequestBody CourseStudentReqDto courseStudentReqDto){
        //validar datos
        int numberId = courseStudentReqDto.getNumberId();
        int dni = courseStudentReqDto.getDni();
        CustomValidation.validateNumberId(numberId);
        CustomValidation.validateDni(dni);

        //pedir id del curso a servicecurso
        CourseInsDto courseInsDto = courseService.obtenerCourseIns(numberId);
        //pedir id del estudiante a servicestudent
        StudentInsDto studentInsDto = studentService.obtenerStudentIns(dni);

        courseStudentService.eliminarInscripcion(courseInsDto.getId(),studentInsDto.getId());

        return new ResponseEntity<>("Inscripcion eliminada con exito",HttpStatus.OK);
    }

    /**
     * Añadir la nota del estudiante en el curso
     * @param calificationDto contiene: dni, numberId, calificacion
     * @return String
     */
    @PostMapping("/inscriptions/qualify")
    public ResponseEntity<String> calificar(@RequestBody CalificationDto calificationDto){
        //Validar
        int numberId = calificationDto.getNumberId();
        int dni = calificationDto.getDni();
        int calification = calificationDto.getCalification();
        CustomValidation.validateNumberId(numberId);
        CustomValidation.validateDni(dni);
        CustomValidation.validateCalification(calification);

        //pedir id del curso a servicecurso
        CourseInsDto courseInsDto = courseService.obtenerCourseIns(numberId);
        //pedir id del estudiante a servicestudent
        StudentInsDto studentInsDto = studentService.obtenerStudentIns(dni);
        //Calificar
        courseStudentService.agregarNota(courseInsDto.getId(),studentInsDto.getId(),calification);
        return new ResponseEntity<>("Nota guardada con exito",HttpStatus.OK);
    }

}
