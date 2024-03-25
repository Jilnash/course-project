package com.jilnash.courseproject.model.education;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
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
    @JsonIgnore
    private HwResponse hwResponse;

    private Date createdAt;

    public Comment(String note, TimeRange timeRange, HwResponse savedResponse) {
        this.note = note;
        this.timeRange = timeRange;
        this.hwResponse = savedResponse;
    }
}
