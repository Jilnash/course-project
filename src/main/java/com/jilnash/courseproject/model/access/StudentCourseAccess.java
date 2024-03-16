package com.jilnash.courseproject.model.access;

import com.jilnash.courseproject.model.education.Course;
import com.jilnash.courseproject.model.participants.Student;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "student_course_access")
@NoArgsConstructor
public class StudentCourseAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Course course;

    @Column(name = "start_date")
    @CreationTimestamp
    private Date startDate;

    private Date endDate;

    public StudentCourseAccess(Student student, Course course, Date endDate) {
        this.student = student;
        this.course = course;
        this.startDate = Date.valueOf(LocalDate.now());
        this.endDate = endDate;
    }
}
