package com.jilnash.courseproject.controller;

import com.jilnash.courseproject.dto.LoginFormDTO;
import com.jilnash.courseproject.dto.RegisterFormDTO;
import com.jilnash.courseproject.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginFormDTO loginFormDTO) {

        return authService.createToken(loginFormDTO);
    }

    @PutMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody RegisterFormDTO registerFormDTO) {

        return authService.createUser(registerFormDTO);
    }
}
