package com.jilnash.courseproject.model.participants;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Data
@Entity
@Table(name = "admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    private String name;

    private String surname;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = " updated_at")
    @UpdateTimestamp
    private Date updatedAt;

}
