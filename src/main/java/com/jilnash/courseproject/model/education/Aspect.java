package com.jilnash.courseproject.model.education;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "aspect")
public class Aspect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}