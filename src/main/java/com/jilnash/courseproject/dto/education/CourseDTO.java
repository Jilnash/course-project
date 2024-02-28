package com.jilnash.courseproject.dto.education;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseDTO {
    private Long adminId;
    private String name;
    private String duration;
}
