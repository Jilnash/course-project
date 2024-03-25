package com.jilnash.courseproject.model.access;

import com.jilnash.courseproject.model.education.Course;
import com.jilnash.courseproject.model.participants.Student;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.time.LocalDate;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "student_course_access")
public class StudentCourseAccess {

    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Student student;

    @ManyToOne
    @JoinColumn
    private Course course;

    @ToString.Include
    @Column(name = "start_date")
    @CreationTimestamp
    private Date startDate;

    @ToString.Include
    private Date endDate;

    public StudentCourseAccess(Student student, Course course, Date endDate) {
        this.student = student;
        this.course = course;
        this.startDate = Date.valueOf(LocalDate.now());
        this.endDate = endDate;
    }
}
