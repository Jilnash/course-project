package com.jilnash.courseproject.model.education;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "time_range")
public class TimeRange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double start;

    private Double end;
}
