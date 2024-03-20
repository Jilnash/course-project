package com.jilnash.courseproject.model.education;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "task_aspect_level")
@NoArgsConstructor
public class TaskAspectLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "aspect_id")
    private Aspect aspect;

    private Double level;

    public TaskAspectLevel(Aspect aspect, Task task, Double level) {
        this.aspect = aspect;
        this.task = task;
        this.level = level;
    }
}
