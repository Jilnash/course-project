package com.jilnash.courseproject.dto.request.participants;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeacherDTO {

    @NotNull(message = "Name is mandatory")
    @NotBlank(message = "Name must not be empty")
    private String name;

    @NotNull(message = "Surname is mandatory")
    @NotBlank(message = "Surname must not be empty")
    private String surname;

    @NotNull(message = "Description is mandatory")
    @NotBlank(message = "Description must not be empty")
    private String description;

    private String photo;

    private String mediaLink;
}
