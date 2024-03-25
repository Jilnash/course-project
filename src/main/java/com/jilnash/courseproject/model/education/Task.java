package com.jilnash.courseproject.model.education;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jilnash.courseproject.model.participants.Teacher;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"taskAspectLevels", "prerequisites"})
@Entity
@Table(name = "task")
@Audited
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotAudited
    @ManyToOne
    @JoinColumn
    private Teacher author;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Course course;

    private String title;

    private String description;

    private String videoLink;

    @NotAudited
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskAspectLevel> taskAspectLevels;

    @ManyToMany
    @JoinTable(
            name = "task_prerequisites",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "prerequisite_task_id")
    )
    private List<Task> prerequisites;

    @NotAudited
    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @NotAudited
    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;
}
