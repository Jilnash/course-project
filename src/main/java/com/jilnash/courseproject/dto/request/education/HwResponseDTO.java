package com.jilnash.courseproject.dto.request.education;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class HwResponseDTO {

    @Valid
    @NotNull(message = "Comments must not be null")
    @NotEmpty(message = "Comments must not be empty")
    private List<CommentDTO> comments;
}
