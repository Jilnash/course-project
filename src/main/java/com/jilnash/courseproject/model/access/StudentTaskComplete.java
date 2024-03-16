package com.jilnash.courseproject.model.access;


import com.jilnash.courseproject.model.education.Task;
import com.jilnash.courseproject.model.participants.Student;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
@Entity
@Table(name = "student_task_complete")
public class StudentTaskComplete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Task task;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;
}
