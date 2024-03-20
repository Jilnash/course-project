package com.jilnash.courseproject.model.education;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jilnash.courseproject.model.participants.Admin;
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
    private Admin createdBy;

    @ManyToOne
    private Admin lastUpdatedBy;

    @ManyToOne
    @JsonIgnore
    private Course course;

    private String description;

    private String videoLink;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TaskAspectLevel> taskAspectLevels;

    @ManyToMany
    @JoinTable(
            name = "task_prerequisites",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "prerequisite_task_id")
    )
    private List<Task> prerequisites;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;
}
