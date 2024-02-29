package com.jilnash.courseproject.dto.request.participants;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentDTO {

    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    @Pattern(
            regexp = "^[A-Z][a-z]{1,20}$",
            message = "Name must start with a capital letter and be between 1 and 20 characters"
    )
    private String name;

    @NotNull(message = "Surname cannot be null")
    @NotEmpty(message = "Surname cannot be empty")
    @Pattern(
            regexp = "^[A-Z][a-z]{1,20}$",
            message = "Surname must start with a capital letter and be between 1 and 20 characters"
    )
    private String surname;

    @NotNull(message = "Skype cannot be null")
    @NotEmpty(message = "Skype cannot be empty")
    private String skype;
}
