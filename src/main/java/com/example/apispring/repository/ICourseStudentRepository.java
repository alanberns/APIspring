package com.example.apispring.repository;

import com.example.apispring.entity.CourseStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICourseStudentRepository extends JpaRepository<CourseStudent,Long> {

    Optional<List<CourseStudent>> findByCourseInsId(Long id);
}
