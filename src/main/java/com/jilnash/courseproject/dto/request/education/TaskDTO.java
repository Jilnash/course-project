package com.jilnash.courseproject.dto.request.education;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskDTO {

    @NotNull(message = "Course id cannot be null")
    @PositiveOrZero(message = "Course id must be positive")
    private Long courseId;

    @Size(min = 20, max = 1000, message = "Description must be between 20 and 1000 characters")
    private String description;

    private String videoLink;
}
