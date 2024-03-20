package com.jilnash.courseproject.dto.request.education;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TaskAspectLevelDTO {

    @NotNull(message = "Aspect id cannot be null")
    @Positive(message = "Aspect id must be positive")
    private Long aspectId;

    @NotNull(message = "Level cannot be null")
    @Positive(message = "Level must be positive")
    @Max(value = 10, message = "Level must be less than 10")
    private Double level;
}
