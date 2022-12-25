package com.example.apispring.service;

import com.example.apispring.dto.StudentDto;
import com.example.apispring.entity.Student;
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

    @Override
    public boolean agregarEstudiante(StudentDto student) {

        ObjectMapper mapper = new ObjectMapper();
        Student s = mapper.convertValue(student, Student.class);
        studentRepository.save(s);
        return true;
    }

    @Override
    public StudentDto buscarEstudiante(int dni) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Optional<Student> student = studentRepository.findByDni(dni);
        if (! student.isPresent()){
            //throw new NotFoundExceptionHandler("No se encontro un usuario");
        }
        Student s = student.get();
        StudentDto studentDto = mapper.convertValue(s, StudentDto.class);
        return studentDto;
    }

}
