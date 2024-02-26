package com.jilnash.courseproject.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("students")
public class StudentController {

    @GetMapping
    public String getStudents() {
        return "Students";
    }

    @PostMapping
    public boolean createStudent() {
        return true;
    }

    @GetMapping("{id}")
    public String getStudent(@PathVariable Long id) {
        return "Student: " + id;
    }

    @PatchMapping("{id}")
    public boolean updateStudent(@PathVariable Long id) {
        return true;
    }
}
