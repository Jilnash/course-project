package com.jilnash.courseproject.dto.request.education;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CourseDTO {

    @NotNull(message = "Name must not be null")
    @NotBlank(message = "Course name must not be empty")
    private String name;

    @NotNull(message = "Duration must not be null")
    @NotBlank(message = "Duration must not be empty")
    private String duration;

    @NotNull(message = "Description must not be null")
    @NotBlank(message = "Description must not be empty")
    @Size(min = 10, message = "Description must be at least 10 characters long")
    private String description;

    @NotNull(message = "HwPostingDayInterval must not be null")
    @Positive(message = "HwPostingDayInterval must be positive integer")
    private Integer hwPostingDayInterval;

    @NotEmpty(message = "Aspects must not be empty")
    private List<@Valid AspectDTO> aspects;
}
