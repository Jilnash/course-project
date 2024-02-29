package com.jilnash.courseproject.dto.request.participants;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {

    @NotNull(message = "Login cannot be null")
    @NotBlank(message = "Login cannot be empty")
    @Pattern(
            regexp = "^[a-zA-Z0-9-_]{3,20}$",
            message = "Login must be between 3 and 20 characters and contain only letters and numbers"
    )
    private String login;

    @NotNull(message = "Phone cannot be null")
    @NotBlank(message = "Phone cannot be empty")
    @Pattern(
            regexp = "^(\\+7|8)\\d{10}$",
            message = "Phone must be in format +7XXXXXXXXXX or 8XXXXXXXXXX"
    )
    private String phone;

    @Email(message = "Email is not valid")
    private String email;
}
