package com.jilnash.courseproject.controller;

import com.jilnash.courseproject.dto.request.participants.StudentDTO;
import com.jilnash.courseproject.dto.response.AppResponse;
import com.jilnash.courseproject.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<?> getStudents() {
        return ResponseEntity.ok(
                new AppResponse(
                        "List of students",
                        200,
                        studentService.getStudents()
                )
        );
    }

    @PutMapping
    public ResponseEntity<?> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok(
                new AppResponse(
                        "Student created successfully",
                        200,
                        studentService.createStudent(studentDTO)
                )
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getStudent(@PathVariable Long id) {
        return ResponseEntity.ok(
                new AppResponse(
                        "Student found successfully",
                        200,
                        studentService.getStudent(id)
                )
        );
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id,
                                 @Valid @RequestBody StudentDTO studentDTO) {

        return ResponseEntity.ok(
                new AppResponse(
                        "Student updated successfully",
                        200,
                        studentService.updateStudent(id, studentDTO)
                )
        );
    }

    @PostMapping("/{id}/purchase/{courseId}")
    public ResponseEntity<?> purchaseCourse(@PathVariable Long id, @PathVariable Long courseId) {
        return ResponseEntity.ok(
                new AppResponse(
                        "Course purchased successfully",
                        200,
                        studentService.purchaseCourse(id, courseId)
                )
        );
    }
}
