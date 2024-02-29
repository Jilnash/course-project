package com.jilnash.courseproject.dto.request.auth;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterFormDTO {

    @NotNull(message = "Login cannot be null")
    @NotBlank(message = "Login cannot be empty")
    @Pattern(
            regexp = "^[a-zA-Z0-9-_]{3,20}$",
            message = "Login must be between 3 and 20 characters " +
                    "and contain only letters, numbers and _ or -"
    )
    private String login;

    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email is not valid")
    private String email;

    @NotNull(message = "Phone cannot be null")
    @NotBlank(message = "Phone cannot be empty")
    @Pattern(
            regexp = "^(\\+7|8)\\d{10}$",
            message = "Phone is not valid"
    )
    private String phone;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
            message = "Password must contain at least one uppercase letter, one lowercase letter and one number"
    )
    private String password;

    @NotNull(message = "Confirm password cannot be null")
    @NotBlank(message = "Confirm password cannot be empty")
    private String confirmPassword;
}
