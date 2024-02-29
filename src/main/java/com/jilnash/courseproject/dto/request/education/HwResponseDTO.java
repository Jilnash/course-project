package com.jilnash.courseproject.dto.request.education;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class HwResponseDTO {

    @NotNull(message = "Teacher id must not be null")
    @PositiveOrZero(message = "Teacher id must be positive or zero")
    private Long teacherId;

    @Valid
    @NotNull(message = "Comments must not be null")
    @NotEmpty(message = "Comments must not be empty")
    private List<CommentDTO> comments;
}
