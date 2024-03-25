package com.jilnash.courseproject.model.education;

import com.jilnash.courseproject.model.participants.Teacher;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@ToString(exclude = {"comments"})
@Entity
@Table(name = "hw_response")
public class HwResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Teacher teacher;

    @ManyToOne
    @JoinColumn
    private Homework homework;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @OneToMany(mappedBy = "hwResponse")
    private List<Comment> comments;
}
