package com.jilnash.courseproject.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterFormDTO {
    private String login;
    private String email;
    private String phone;
    private String password;
    private String confirmPassword;
}
