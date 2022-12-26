package com.example.apispring.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 60)
    private String name;
    @Column(nullable = false)
    private int year;
    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false, unique = true)
    private int numberId;

    @JsonManagedReference
    @OneToMany(mappedBy = "courseIns")
    private List<CourseStudent> students;
}
