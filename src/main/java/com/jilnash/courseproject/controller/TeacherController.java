package com.jilnash.courseproject.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("teacher")
public class TeacherController {

    @GetMapping
    public String getTeacher() {
        return "Teachers";
    }

    @PostMapping
    public boolean createTeacher() {
        return true;
    }

    @GetMapping("{id}")
    public String getTeacherById(@PathVariable Long id) {
        return "Teacher: " + id;
    }

    @PatchMapping("{id}")
    public boolean updateTeacher(@PathVariable Long id) {
        return true;
    }
}
