package com.example.apispring.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "course_student")
public class CourseStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course courseIns;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student studentIns;

    @Column()
    private int calification;
}
