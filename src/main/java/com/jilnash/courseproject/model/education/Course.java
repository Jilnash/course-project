package com.jilnash.courseproject.model.education;

import com.jilnash.courseproject.model.participants.Teacher;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "course")
@Audited
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String duration;

    @NotAudited
    @ManyToOne
    private Teacher author;

    @NotAudited
    @ManyToMany
    @JoinTable(
            name = "teacher_course_access",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    private List<Teacher> teachers;

    @NotAudited
    @OneToMany(mappedBy = "course")
    private List<Task> tasks;

    @NotAudited
    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @NotAudited
    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;
}
