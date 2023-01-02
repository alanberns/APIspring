package com.example.apispring.service;

import com.example.apispring.dto.response.StudentDto;
import com.example.apispring.dto.StudentInsDto;
import com.example.apispring.entity.Student;
import com.example.apispring.exception.NotFoundExceptionHandler;
import com.example.apispring.repository.IStudentRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService implements IStudentService{
    private IStudentRepository studentRepository;

    public StudentService(IStudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    /**
     * Obtener la lista de estudiantes
     * @return lista de estudiantes
     */
    @Override
    public List<StudentDto> obtenerEstudiantes(){
        ObjectMapper mapper = new ObjectMapper();
        List<Student> students = studentRepository.findAll();
        List<StudentDto> studentDtos = students
                .stream()
                .map( student -> {
            return new StudentDto(student.getFirstName(),student.getLastName(),student.getDni(),student.isActive());
        })
                .collect(Collectors.toList());
        return studentDtos;
    }

    /**
     * Crear un nuevo esudiante
     * @param studentDto datos del estudiante
     * @return true
     */
    @Override
    public boolean agregarEstudiante(StudentDto studentDto) {

        ObjectMapper mapper = new ObjectMapper();
        Student s = mapper.convertValue(studentDto, Student.class);
        studentRepository.save(s);
        return true;
    }

    /**
     * Buscar un estudiante por dni
     * @param dni dni del estudiante
     * @return datos del estudiante
     */
    @Override
    public StudentDto buscarEstudiante(int dni) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Optional<Student> student = studentRepository.findByDni(dni);
        if (! student.isPresent()){
            throw new NotFoundExceptionHandler("No se encontro un usuario");
        }
        Student s = student.get();
        StudentDto studentDto = mapper.convertValue(s, StudentDto.class);
        return studentDto;
    }

    /**
     * Eliminar un estudiante. Eliminacion logica
     * @param dni dni del estudiante
     * @return retorna los datos del estudiante
     */
    @Override
    public StudentDto eliminarEstudiante(StudentDto studentDto) {
        studentDto.setActive(false);
        modificarEstudiante(studentDto);
        return studentDto;
    }

    /**
     * Modificar estudiante
     * @param studentDto datos del estudiante
     * @return los datos del estudiante
     */
    @Override
    public StudentDto modificarEstudiante(StudentDto studentDto){
        Optional <Student> student = studentRepository.findByDni(studentDto.getDni());
        if (! student.isPresent()){
            throw new NotFoundExceptionHandler("No se encontro un usuario");
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Student s = student.get();
        s.setActive(studentDto.isActive());
        s.setDni(studentDto.getDni());
        s.setFirstName(studentDto.getFirstName());
        s.setLastName(studentDto.getLastName());
        studentRepository.save(s);
        StudentDto sDto = mapper.convertValue(s,StudentDto.class);
        return sDto;
    }

    /**
     * Buscar un estudiante por dni
     * @param dni dni del estudiante
     * @return datos del estudiante (id privada)
     */
    @Override
    public StudentInsDto obtenerStudentIns(int dni) {
        Optional<Student> student = studentRepository.findByDni(dni);
        if (! student.isPresent()){
            throw new NotFoundExceptionHandler("No se encontro un usuario");
        }
        Student s = student.get();
        return new StudentInsDto(s.getId(),s.getFirstName(),s.getLastName());
    }

}
