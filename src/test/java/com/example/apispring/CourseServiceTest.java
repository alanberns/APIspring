package com.example.apispring;

import com.example.apispring.dto.CourseInsDto;
import com.example.apispring.dto.response.CourseDto;
import com.example.apispring.entity.Course;
import com.example.apispring.exception.NotFoundExceptionHandler;
import com.example.apispring.repository.ICourseRepository;
import com.example.apispring.service.CourseService;
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
class CourseServiceTest {

	@Mock
	private ICourseRepository courseRepository;

	@InjectMocks
	private CourseService courseService;


	@Test
	void obtenerCursosTest() {
		//Arrange
		CourseDto courseDto1 = new CourseDto("Matematica 1", 2022, true, 45);
		CourseDto courseDto2 = new CourseDto("Quimica 1", 2022, true, 90);
		Course course1 = new Course(1L,"Matematica 1", 2022, true, 45,null);
		Course course2 = new Course(2L,"Quimica 1", 2022, true, 90,null);
		List<Course> courses = new ArrayList<>();
		courses.add(course1);
		courses.add(course2);

		List<CourseDto> coursesEsperados = new ArrayList<>();
		coursesEsperados.add(courseDto1);
		coursesEsperados.add(courseDto2);
		//act
		when(courseRepository.findAll()).thenReturn(courses);
		List<CourseDto> coursesObtenidos = courseService.obtenerCursos();
		//assert
		assertEquals(coursesEsperados.size(),coursesObtenidos.size());
		assertEquals("Quimica 1",coursesObtenidos.get(1).getName());
	}

	@Test
	void obtenerCursosEmptyTest(){
		//arrange
		List<Course> courses = new ArrayList<>();
		//act
		when(courseRepository.findAll()).thenReturn(courses);
		List<CourseDto> coursesObtenidos = courseService.obtenerCursos();
		//assert
		assertEquals(0,coursesObtenidos.size());
	}

	@Test
	void crearCursoTest(){
		//Arrange
		CourseDto courseDto = new CourseDto("Matematica 2",2022,true,46);
		//act
		//assert
		assertTrue(courseService.crearCurso(courseDto));
	}

	@Test
	void modificarCursoTest(){
		//arrange
		Course course = new Course(1L,"Matematica 1",2022,true,45,null);
		Optional<Course> optionalCourse = Optional.of(course);
		CourseDto courseModificado = new CourseDto("Quimica 1",2022,true,45);
		//act
		when(courseRepository.findCourseByNumberId(course.getNumberId())).thenReturn(optionalCourse);
		CourseDto courseObtenido = courseService.modificarCurso(courseModificado);
		//assert
		assertEquals(courseModificado.getName(),courseObtenido.getName());
	}

	@Test
	void modificarCursoErrorTest(){
		//arrange
		CourseDto courseModificado = new CourseDto("Quimica 1",2022,true,45);
		//act
		when(courseRepository.findCourseByNumberId(courseModificado.getNumberId())).thenReturn(Optional.empty());
		//assert
		assertThrows(NotFoundExceptionHandler.class,() -> courseService.modificarCurso(courseModificado),"No se encontro un curso" );
	}

	@Test
	void eliminarCursoTest(){
		//arrange
		CourseDto courseDto = new CourseDto("Matematica 1",2022,true,45);
		Course course = new Course(1L,"Matematica 1",2022,true,45,null);
		Optional<Course> optionalCourse = Optional.of(course);
		//act
		when(courseRepository.findCourseByNumberId(courseDto.getNumberId())).thenReturn(optionalCourse);
		CourseDto courseDtoObtenido = courseService.eliminarCurso(courseDto);
		//assert
		assertFalse(courseDtoObtenido.isActive());
	}

	@Test
	void buscarCursoTest(){
		//arrange
		CourseDto courseDtoEsperado = new CourseDto("Matematica 1",2022,true,45);
		Course course = new Course(1L,"Matematica 1",2022,true,45,null);
		Optional<Course> optionalCourse = Optional.of(course);
		//act
		when(courseRepository.findCourseByNumberId(courseDtoEsperado.getNumberId())).thenReturn(optionalCourse);
		CourseDto courseDtoObtenido = courseService.buscarCurso(courseDtoEsperado.getNumberId());
		//assert
		assertEquals(courseDtoEsperado.getName(),courseDtoObtenido.getName());
	}

	@Test
	void buscarCursoErrorTest(){
		//arrange
		CourseDto course = new CourseDto("Quimica 1",2022,true,45);
		//act
		when(courseRepository.findCourseByNumberId(course.getNumberId())).thenReturn(Optional.empty());
		//assert
		assertThrows(NotFoundExceptionHandler.class,() -> courseService.buscarCurso(course.getNumberId()),"No se encontro un curso" );
	}

	@Test
	void obtenerCourseInsTest(){
		//arrange
		CourseDto courseDto = new CourseDto("Matematica 1",2022,true,45);
		Course course = new Course(1L,"Matematica 1",2022,true,45,null);
		Optional<Course> optionalCourse = Optional.of(course);
		CourseInsDto courseInsDtoEsperado = new CourseInsDto(1L,"Matematica 1",2022);
		//act
		when(courseRepository.findCourseByNumberId(courseDto.getNumberId())).thenReturn(optionalCourse);
		CourseInsDto courseInsDtoObtenido = courseService.obtenerCourseIns(courseDto.getNumberId());
		//assert
		assertEquals(courseInsDtoEsperado.getId(),courseInsDtoObtenido.getId());
		assertEquals(courseInsDtoEsperado.getName(),courseInsDtoObtenido.getName());
	}

	@Test
	void obtenerCursoInsErrorTest(){
		//arrange
		CourseDto course = new CourseDto("Quimica 1",2022,true,45);
		//act
		when(courseRepository.findCourseByNumberId(course.getNumberId())).thenReturn(Optional.empty());
		//assert
		assertThrows(NotFoundExceptionHandler.class,() -> courseService.obtenerCourseIns(course.getNumberId()),"No se encontro un curso" );
	}
}
