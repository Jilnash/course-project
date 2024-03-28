package com.jilnash.courseproject.dto.request.education;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TaskDTO {

    @NotNull(message = "Title cannot be null")
    @NotEmpty(message = "Title cannot be empty")
    @Size(min = 10, max = 30, message = "Title must be between 10 and 30 characters")
    private String title;

    @Size(min = 20, max = 1000, message = "Description must be between 20 and 1000 characters")
    private String description;

    private String videoLink;

    @NotNull(message = "Audio required cannot be null")
    private Boolean audioRequired;

    @NotNull(message = "Video required cannot be null")
    private Boolean videoRequired;

    @NotNull(message = "Prerequisites cannot be null")
    private List<@Positive(message = "prereq id must by positive") Long> prerequisites;

    @NotNull(message = "Task aspect levels cannot be null")
    @NotEmpty(message = "Task aspect levels cannot be empty")
    private List<@Valid TaskAspectLevelDTO> taskAspectLevels;
}
