package com.jilnash.courseproject.dto.request.auth;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasswordChangeDTO {

    @NotNull(message = "Old password must not be null")
    @NotBlank(message = "Old password must not be empty")
    private String oldPassword;

    @NotNull(message = "New password must not be null")
    @NotBlank(message = "New password must not be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
            message = "Password must contain at least one uppercase letter, one lowercase letter and one number"
    )
    private String newPassword;

    @NotNull(message = "Confirm new password must not be null")
    @NotBlank(message = "Confirm new password must not be empty")
    private String confirmNewPassword;
}
