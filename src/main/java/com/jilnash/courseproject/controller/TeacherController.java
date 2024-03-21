package com.jilnash.courseproject.controller;

import com.jilnash.courseproject.dto.request.participants.TeacherDTO;
import com.jilnash.courseproject.dto.response.AppResponse;
import com.jilnash.courseproject.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping
    public ResponseEntity<?> getTeachers() {
        return ResponseEntity.ok(
                new AppResponse(
                        "List of teachers",
                        200,
                        teacherService.getTeachers()
                )
        );
    }

    @PutMapping
    public ResponseEntity<?> createTeacher(@Valid @RequestBody TeacherDTO teacherDTO) {

        return ResponseEntity.ok(
                new AppResponse(
                        "Teacher created successfully",
                        200,
                        teacherService.createTeacher(teacherDTO)
                )
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getTeacherById(@PathVariable Long id) {

        return ResponseEntity.ok(
                new AppResponse(
                        "Teacher found successfully",
                        200,
                        teacherService.getTeacher(id)
                )
        );
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> updateTeacher(@PathVariable Long id,
                                           @Valid @RequestBody TeacherDTO teacherDTO) {

        return ResponseEntity.ok(
                new AppResponse(
                        "Teacher updated successfully",
                        200,
                        teacherService.updateTeacher(id, teacherDTO)
                )
        );
    }
}
