package com.example.apispring;

import com.example.apispring.dto.CourseInsDto;
import com.example.apispring.dto.StudentInsDto;
import com.example.apispring.dto.response.CourseStudentDto;
import com.example.apispring.entity.Course;
import com.example.apispring.entity.CourseStudent;
import com.example.apispring.entity.Student;
import com.example.apispring.exception.NotFoundExceptionHandler;
import com.example.apispring.repository.ICourseRepository;
import com.example.apispring.repository.ICourseStudentRepository;
import com.example.apispring.repository.IStudentRepository;
import com.example.apispring.service.CourseStudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CourseStudentServiceTest {

    @Mock
    private ICourseStudentRepository courseStudentRepository;
    @Mock
    private ICourseRepository courseRepository;
    @Mock
    private IStudentRepository studentRepository;

    @InjectMocks
    private CourseStudentService courseStudentService;


    @Test
    void obtenerInscripcionesCourseTest(){
        //arrange
        CourseInsDto courseInsDto = new CourseInsDto(1L,"Matematica 1", 2022);
        Course course = new Course(1L,"Matematica 1",2022,true,45,null);
        Student student1 = new Student(10L,"Marcos","Moreira",40123456,true,null);
        Student student2 = new Student(50L,"Daniela","Gomez",30000456,true,null);
        CourseStudent ins1 = new CourseStudent(100L,course,student1,10);
        CourseStudent ins2 = new CourseStudent(200L,course,student2,9);
        List<CourseStudent> inscriptions = new ArrayList<>();
        inscriptions.add(ins1);
        inscriptions.add(ins2);
        //act
        when(courseStudentRepository.findByCourseInsId(courseInsDto.getId())).thenReturn(Optional.of(inscriptions));
        List<CourseStudentDto> inscripcionesObtenidas = courseStudentService.obtenerInscripciones(courseInsDto);
        //assert
        assertEquals(inscriptions.size(),inscripcionesObtenidas.size());
        assertEquals(inscriptions.get(0).getCalification(),inscripcionesObtenidas.get(0).getCalification());
        assertEquals("Daniela",inscripcionesObtenidas.get(1).getFirstName());
        assertEquals(inscriptions.get(0).getCourseIns().getYear(),inscripcionesObtenidas.get(0).getYear());
    }

    @Test
    void obtenerInscripcionesCourseErrorTest(){
        //arrange
        CourseInsDto courseInsDto = new CourseInsDto(1L,"Matematica 1", 2022);
        List<CourseStudent> inscriptions = new ArrayList<>();
        //act
        when(courseStudentRepository.findByCourseInsId(courseInsDto.getId())).thenReturn(Optional.of(inscriptions));
        //assert
        assertThrows(
                NotFoundExceptionHandler.class,
                ()->courseStudentService.obtenerInscripciones(courseInsDto),
                ("No se encontraron inscripciones")
        );
    }

    @Test
    void obtenerInscripcionesStudentTest(){
        //arrange
        StudentInsDto studentInsDto = new StudentInsDto(1L,"Daniela","Gomez");
        Course course = new Course(55L,"Matematica 1",2022,true,45,null);
        Course course2 = new Course(56L,"Quimica 1",2022,true,55,null);
        Student student = new Student(50L,"Daniela","Gomez",30000456,true,null);
        CourseStudent ins1 = new CourseStudent(100L,course,student,10);
        CourseStudent ins2 = new CourseStudent(101L,course2,student,9);
        List<CourseStudent> inscriptions = new ArrayList<>();
        inscriptions.add(ins1);
        inscriptions.add(ins2);
        //act
        when(courseStudentRepository.findByStudentInsId(studentInsDto.getId())).thenReturn(Optional.of(inscriptions));
        when(courseRepository.findById(ins1.getCourseIns().getId())).thenReturn(Optional.of(course));
        when(courseRepository.findById(ins2.getCourseIns().getId())).thenReturn(Optional.of(course2));
        List<CourseStudentDto> inscripcionesObtenidas = courseStudentService.obtenerInscripciones(studentInsDto);
        //assert
        assertEquals(inscriptions.size(),inscripcionesObtenidas.size());
        assertEquals(inscriptions.get(0).getCalification(),inscripcionesObtenidas.get(0).getCalification());
        assertEquals("Daniela",inscripcionesObtenidas.get(0).getFirstName());
        assertEquals(inscriptions.get(0).getCourseIns().getYear(),inscripcionesObtenidas.get(0).getYear());
    }

    @Test
    void obtenerInscripcionesStudentErrorTest(){
        //arrange
        StudentInsDto studentInsDto = new StudentInsDto(1L,"Daniela","Gomez");
        List<CourseStudent> inscriptions = new ArrayList<>();
        //act
        when(courseStudentRepository.findByStudentInsId(studentInsDto.getId())).thenReturn(Optional.of(inscriptions));
        //assert
        assertThrows(
                NotFoundExceptionHandler.class,
                ()->courseStudentService.obtenerInscripciones(studentInsDto),
                ("No se encontraron inscripciones")
        );
    }

    @Test
    void agregarInscripcionTest(){
        //arrange
        Long id_course = 1L;
        Long id_student = 10L;
        Course course = new Course(id_course,"Matematica 1",2022,true,45,null);
        Student student = new Student(id_student,"Daniela","Gomez",43987123,true,null);
        //act
        when(courseRepository.findById(id_course)).thenReturn(Optional.of(course));
        when(studentRepository.findById(id_student)).thenReturn(Optional.of(student));
        //assert
        assertTrue(courseStudentService.agregarInscripcion(id_course,id_student));
    }

    @Test
    void agregarInscripcionErrorTest(){
        //arrange
        Long id_course = 1L;
        Long id_student = 10L;
        Course course = new Course(id_course,"Matematica 1",2022,true,45,null);
        //act
        when(courseRepository.findById(id_course)).thenReturn(Optional.of(course));
        when(studentRepository.findById(id_student)).thenReturn(Optional.empty());
        //assert
        assertThrows(NotFoundExceptionHandler.class,()->courseStudentService.agregarInscripcion(id_course,id_student),
                "No se encontro el estudiante");
    }

    @Test
    void estaInscriptoTest(){
        //arrange
        Long id_course = 1L;
        Long id_student = 10L;
        Course course = new Course(id_course,"Matematica 1",2022,true,45,null);
        Student student = new Student(id_student,"Daniela","Gomez",43987123,true,null);
        CourseStudent courseStudent = new CourseStudent(90L,course,student,5);
        //act
        when(courseStudentRepository.findFirstByCourseInsIdAndStudentInsId(id_course,id_student)).thenReturn(Optional.of(courseStudent));
        //assert
        assertThrows(
                NotFoundExceptionHandler.class,
                ()->courseStudentService.estaInscripto(id_course,id_student),
                "El estudiante ya se encuentra inscripto");
    }

    @Test
    void agregarNotaTest(){
        //arrange
        Long id_course = 1L;
        Long id_student = 10L;
        int nota = 10;
        Course course = new Course(id_course,"Matematica 1",2022,true,45,null);
        Student student = new Student(id_student,"Daniela","Gomez",43987123,true,null);
        CourseStudent courseStudent = new CourseStudent(90L,course,student,-1);
        //act
        when(courseStudentRepository.findFirstByCourseInsIdAndStudentInsId(id_course,id_student)).thenReturn(Optional.of(courseStudent));
        //assert
        assertTrue(courseStudentService.agregarNota(id_course,id_student,nota));
    }

    @Test
    void agregarNotaErrorTest(){
        //arrange
        Long id_course = 1L;
        Long id_student = 10L;
        int nota = 10;
        //act
        when(courseStudentRepository.findFirstByCourseInsIdAndStudentInsId(id_course,id_student)).thenReturn(Optional.empty());
        //assert
        assertThrows(
                NotFoundExceptionHandler.class,
                ()->courseStudentService.agregarNota(id_course,id_student,nota),
                "No se encontro la inscripcion"
                );
    }

    @Test
    void eliminarInscripcionTest(){
        //arrange
        Long id_course = 1L;
        Long id_student = 10L;
        Course course = new Course(id_course,"Matematica 1",2022,true,45,null);
        Student student = new Student(id_student,"Daniela","Gomez",43987123,true,null);
        CourseStudent courseStudent = new CourseStudent(90L,course,student,-1);
        //act
        when(courseStudentRepository.findFirstByCourseInsIdAndStudentInsId(id_course,id_student)).thenReturn(Optional.of(courseStudent));
        //assert
        assertTrue(courseStudentService.eliminarInscripcion(id_course,id_student));
    }

    @Test
    void eliminarInscripcionErrorTest(){
        //arrange
        Long id_course = 1L;
        Long id_student = 10L;
        //act
        when(courseStudentRepository.findFirstByCourseInsIdAndStudentInsId(id_course,id_student)).thenReturn(Optional.empty());
        //assert
        assertThrows(
                NotFoundExceptionHandler.class,
                ()->courseStudentService.eliminarInscripcion(id_course,id_student),
                "No se encontro la inscripcion");
    }

}
