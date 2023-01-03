package com.example.apispring.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
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

    @JsonManagedReference(value = "course-cs")
    @OneToMany(mappedBy = "courseIns")
    private List<CourseStudent> students;
}
