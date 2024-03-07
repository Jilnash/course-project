package com.jilnash.courseproject.dto.request.education;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseDTO {

    @NotNull(message = "Admin id must not be null")
    @PositiveOrZero(message = "Admin id must be positive")
    private Long adminId;

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
}
