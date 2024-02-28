package com.jilnash.courseproject.controller;

import com.jilnash.courseproject.dto.auth.LoginFormDTO;
import com.jilnash.courseproject.dto.auth.RegisterFormDTO;
import com.jilnash.courseproject.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody LoginFormDTO loginFormDTO) {

        return authService.createToken(loginFormDTO);
    }

    @PutMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody RegisterFormDTO registerFormDTO) {

        return authService.createUser(registerFormDTO);
    }
}
