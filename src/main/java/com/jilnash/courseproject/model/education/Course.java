package com.jilnash.courseproject.model.education;

import com.jilnash.courseproject.model.participants.Admin;
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
    private Admin createdBy;

    @NotAudited
    @ManyToOne
    private Admin updatedBy;

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
