package com.jilnash.courseproject.controller;

import com.jilnash.courseproject.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    AuthService authService;

    @GetMapping
    public String getUsers() {
        return "Users";
    }

    @GetMapping("{id}")
    public String getUser(@PathVariable Long id) {
        return "User: " + id;
    }

    @PatchMapping("{id}")
    public String updateUser(@PathVariable Long id) {
        return "User: " + id;
    }
}
