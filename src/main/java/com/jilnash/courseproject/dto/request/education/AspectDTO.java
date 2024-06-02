package com.jilnash.courseproject.dto.request.education;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AspectDTO {

    private Long id;

    @NotNull(message = "Aspect name must not be null")
    @NotBlank(message = "Aspect name must not be empty")
    private String name;
}
