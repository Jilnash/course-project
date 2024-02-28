package com.jilnash.courseproject.dto.education;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskDTO {
    private Long courseId;
    private Long adminId;
    private String description;
    private String videoLink;
}
