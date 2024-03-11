package com.jilnash.courseproject.model.education;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "comment")
@NoArgsConstructor
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

    public Comment(String note, TimeRange timeRange, HwResponse savedResponse) {
        this.note = note;
        this.timeRange = timeRange;
        this.hwResponse = savedResponse;
    }
}
