package com.jilnash.courseproject.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginFormDTO {

    @NotNull(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be empty")
    private String username;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be empty")
    private String password;
}
