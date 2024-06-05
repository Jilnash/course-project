package com.jilnash.courseproject.dto.request.participants;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeacherDTO {

    @NotNull(message = "Name is mandatory")
    @NotBlank(message = "Name must not be empty")
    @Pattern(
            regexp = "^[A-ZА-ЯЁ][A-Za-zА-Яа-яЁё]*$",
            message = "Name must start with a capital letter and contain only letters"
    )
    private String name;

    @NotNull(message = "Surname is mandatory")
    @NotBlank(message = "Surname must not be empty")
    @Pattern(
            regexp = "^[A-ZА-ЯЁ][A-Za-zА-Яа-яЁё]*$",
            message = "Surname must start with a capital letter and contain only letters"
    )
    private String surname;

    @NotNull(message = "Description is mandatory")
    @NotBlank(message = "Description must not be empty")
    private String description;

    private String photo;

    private String mediaLink;

    @NotNull(message = "User id is mandatory")
    @NotBlank(message = "User id must not be empty")
    private Long userId;
}
