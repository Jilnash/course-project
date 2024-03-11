package com.jilnash.courseproject.model.education;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "timerange")
@NoArgsConstructor
public class TimeRange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double start;

    private Double end;

    public TimeRange(Double start, Double end) {
        this.start = start;
        this.end = end;
    }
}
