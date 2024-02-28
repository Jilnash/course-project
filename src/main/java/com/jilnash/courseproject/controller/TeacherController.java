package com.jilnash.courseproject.controller;

import com.jilnash.courseproject.dto.participants.TeacherDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("teacher")
public class TeacherController {

    @GetMapping
    public String getTeacher() {
        return "Teachers";
    }

    @PutMapping
    public boolean createTeacher(@RequestBody TeacherDTO teacherDTO) {
        System.out.println(teacherDTO);
        return true;
    }

    @GetMapping("{id}")
    public String getTeacherById(@PathVariable Long id) {
        return "Teacher: " + id;
    }

    @PatchMapping("{id}")
    public boolean updateTeacher(@PathVariable Long id,
                                 @RequestBody TeacherDTO teacherDTO) {
        System.out.println(teacherDTO);
        return true;
    }
}
