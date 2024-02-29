package com.jilnash.courseproject.controller;

import com.jilnash.courseproject.dto.request.participants.StudentDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("students")
public class StudentController {

    @GetMapping
    public String getStudents() {
        return "Students";
    }

    @PutMapping
    public boolean createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        System.out.println(studentDTO);
        return true;
    }

    @GetMapping("{id}")
    public String getStudent(@PathVariable Long id) {
        return "Student: " + id;
    }

    @PatchMapping("{id}")
    public boolean updateStudent(@PathVariable Long id,
                                 @Valid @RequestBody StudentDTO studentDTO) {
        System.out.println(studentDTO);
        return true;
    }
}
