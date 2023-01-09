package com.example.apispring;

import com.example.apispring.dto.StudentInsDto;
import com.example.apispring.dto.response.StudentDto;
import com.example.apispring.entity.Student;
import com.example.apispring.exception.NotFoundExceptionHandler;
import com.example.apispring.repository.IStudentRepository;
import com.example.apispring.service.StudentService;
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
public class StudentServiceTest {

    @Mock
    private IStudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    void obtenerEstudiantesTest(){
        //arrange
        StudentDto studentDto1 = new StudentDto("Daniela","Gomez",43000678,true);
        StudentDto studentDto2 = new StudentDto("Mariano","Tello",19234890,true);
        List<StudentDto> estudiantesEsperados = new ArrayList<>();

        Student student1 = new Student(1L,"Daniela","Gomez",43000678,true,null);
        Student student2 = new Student(2L,"Mariano","Tello",19234890,true,null);
        List<Student> estudiantes = new ArrayList<>();

        estudiantesEsperados.add(studentDto1);
        estudiantesEsperados.add(studentDto2);
        estudiantes.add(student1);
        estudiantes.add(student2);

        //act
        when(studentRepository.findAll()).thenReturn(estudiantes);
        List<StudentDto> estudiantesObtenidos = studentService.obtenerEstudiantes();

        //assert
        assertEquals(estudiantesEsperados.size(),estudiantesObtenidos.size());
        assertEquals(estudiantesEsperados.get(0).getFirstName(),estudiantesObtenidos.get(0).getFirstName());
        assertTrue(estudiantesObtenidos.get(1).isActive());
    }

    @Test
    void obtenerEstudiantesEmptyTest(){
        //arrange
        List<Student> students = new ArrayList<>();
        //act
        when(studentRepository.findAll()).thenReturn(students);
        List<StudentDto> estudiantesObtenidos = studentService.obtenerEstudiantes();
        //assert
        assertEquals(0,estudiantesObtenidos.size());
    }

    @Test
    void agregarEstudianteTest(){
        //arrange
        StudentDto studentDto = new StudentDto("Daniela","Gomez",43000678,true);
        //act
        //assert
        assertTrue(studentService.agregarEstudiante(studentDto));
    }

    @Test
    void buscarEstudianteTest(){
        //arrange
        int dni = 43000678;
        StudentDto studentDtoEsperado = new StudentDto("Daniela","Gomez",43000678,true);
        Student student1 = new Student(1L,"Daniela","Gomez",43000678,true,null);
        //act
        when(studentRepository.findByDni(dni)).thenReturn(Optional.of(student1));
        StudentDto studentDtoObtenido = studentService.buscarEstudiante(dni);
        //assert
        assertEquals(studentDtoEsperado.getFirstName(),studentDtoObtenido.getFirstName());
        assertEquals(studentDtoEsperado.getDni(),studentDtoObtenido.getDni());
        assertTrue(studentDtoObtenido.isActive());
    }

    @Test
    void buscarEstudianteErrorTest(){
        //arrange
        int dni = 43000678;
        //act
        when(studentRepository.findByDni(dni)).thenReturn(Optional.empty());
        //assert
        assertThrows(
                NotFoundExceptionHandler.class,
                ()->studentService.buscarEstudiante(dni),
                "No se encontro al estudiante"
        );
    }

    @Test
    void eliminarEstudianteTest(){
        //arrange
        StudentDto studentDto = new StudentDto("Daniela","Gomez",43000678,true);
        Student student1 = new Student(1L,"Daniela","Gomez",43000678,true,null);
        //act
        when(studentRepository.findByDni(studentDto.getDni())).thenReturn(Optional.of(student1));
        StudentDto studentDtoObtenido = studentService.eliminarEstudiante(studentDto);
        //assert
        assertFalse(studentDtoObtenido.isActive());
    }

    @Test
    void modificarEstudianteTest(){
        StudentDto studentDtoModificado = new StudentDto("Mayra","Gomez",43000678,true);
        Student student1 = new Student(1L,"Daniela","Gomez",43000678,true,null);
        //act
        when(studentRepository.findByDni(studentDtoModificado.getDni())).thenReturn(Optional.of(student1));
        StudentDto studentDtoObtenido = studentService.modificarEstudiante(studentDtoModificado);
        //assert
        assertEquals(studentDtoModificado.getDni(),studentDtoObtenido.getDni());
        assertEquals("Mayra",studentDtoObtenido.getFirstName());
    }

    @Test
    void modificarEstudianteErrorTest(){
        //arrange
        StudentDto studentDto = new StudentDto("Daniela","Gomez",43000678,true);
        //act
        when(studentRepository.findByDni(studentDto.getDni())).thenReturn(Optional.empty());
        //assert
        assertThrows(
                NotFoundExceptionHandler.class,
                ()->studentService.modificarEstudiante(studentDto),
                "No se encontro al estudiante"
        );
    }

    @Test
    void obtenerStudentInsDtoTest(){
        //arrange
        int dni = 43000678;
        Student student1 = new Student(1L,"Daniela","Gomez",43000678,true,null);
        //act
        when(studentRepository.findByDni(dni)).thenReturn(Optional.of(student1));
        StudentInsDto studentInsDtoObtenido = studentService.obtenerStudentIns(dni);
        //assert
        assertEquals(student1.getFirstName(),studentInsDtoObtenido.getFirstName());
        assertEquals(student1.getLastName(),studentInsDtoObtenido.getLastName());
    }

    @Test
    void obtenerStudentInsErrorTest(){
        //arrange
        int dni = 43000678;
        //act
        when(studentRepository.findByDni(dni)).thenReturn(Optional.empty());
        //assert
        assertThrows(
                NotFoundExceptionHandler.class,
                ()->studentService.obtenerStudentIns(dni),
                "No se encontro al estudiante"
        );
    }
}
