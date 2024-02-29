package com.jilnash.courseproject.dto.request.education;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentDTO {

    @PositiveOrZero(message = "Start time must be positive or zero")
    @NotNull(message = "Start time must not be null")
    private Double start;

    @PositiveOrZero(message = "End time must be positive or zero")
    @NotNull(message = "End time must not be null")
    private Double end;

    @NotNull(message = "Note must not be null")
    @NotBlank(message = "Note must not be empty")
    private String note;
}
