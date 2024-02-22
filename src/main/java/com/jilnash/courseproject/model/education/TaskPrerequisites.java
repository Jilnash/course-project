package com.jilnash.courseproject.model.education;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "task_prerequisites")
public class TaskPrerequisites {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Task task;

    @ManyToOne
    private Task prerequisiteTask;
}
