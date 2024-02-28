package com.jilnash.courseproject.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginFormDTO {
    private String username;
    private String password;
}
