package com.example.apispring.repository;

import com.example.apispring.entity.Course;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findCourseByNumberId(int numberId);
}
