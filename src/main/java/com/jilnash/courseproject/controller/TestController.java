package com.jilnash.courseproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/student")
    public String student() {
        return "Student";
    }

    @GetMapping("/teacher")
    public String teacher() {
        return "Teacher";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Admin";
    }
}
