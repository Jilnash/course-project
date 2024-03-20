package com.jilnash.courseproject.dto.request.education;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TaskDTO {

    @NotNull(message = "Course id cannot be null")
    @PositiveOrZero(message = "Course id must be positive")
    private Long courseId;

    @Size(min = 20, max = 1000, message = "Description must be between 20 and 1000 characters")
    private String description;

    private String videoLink;

    @NotNull(message = "Prerequisites cannot be null")
    private List<@Positive(message = "prereq id must by positive") Long> prerequisites;

    @NotNull(message = "Task aspect levels cannot be null")
    @NotEmpty(message = "Task aspect levels cannot be empty")
    private List<@Valid TaskAspectLevelDTO> taskAspectLevels;
}
