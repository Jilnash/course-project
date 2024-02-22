package com.jilnash.courseproject.model.education;

import com.jilnash.courseproject.model.participants.Teacher;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Teacher teacher;

    @ManyToOne
    private Course course;

    private String description;

    private String videoLink;

    @OneToMany(mappedBy = "task")
    private List<TaskAspectLevel> taskAspectLevels;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;
}
