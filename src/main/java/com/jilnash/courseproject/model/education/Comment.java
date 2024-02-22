package com.jilnash.courseproject.model.education;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String note;

    @OneToOne
    private TimeRange timeRange;

    @ManyToOne
    private HwResponse hwResponse;

    private Date createdAt;
}
