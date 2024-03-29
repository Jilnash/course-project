package com.jilnash.courseproject.controller;

import com.jilnash.courseproject.dto.request.education.CourseDTO;
import com.jilnash.courseproject.dto.request.education.TaskDTO;
import com.jilnash.courseproject.dto.response.AppResponse;
import com.jilnash.courseproject.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<?> getAllCourses() {
        return ResponseEntity.ok(
                new AppResponse(
                        "List of courses",
                        200,
                        courseService.getCourses()
                )
        );
    }

    @PutMapping
    public ResponseEntity<?> createCourse(@Valid @RequestBody CourseDTO courseDTO) {

        String teacherLogin = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        return ResponseEntity.ok(
                new AppResponse(
                        "Course created successfully",
                        201,
                        courseService.createCourse(courseDTO, teacherLogin)
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourse(@PathVariable Long id) {
        return ResponseEntity.ok(
                new AppResponse(
                        "Course found successfully",
                        200,
                        courseService.getCourse(id)
                )
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Long id,
                                          @Valid @RequestBody CourseDTO courseDTO) {

        String teacherLogin = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        return ResponseEntity.ok(
                new AppResponse(
                        "Course updated successfully",
                        200,
                        courseService.updateCourse(id, courseDTO, teacherLogin)
                )
        );
    }

    @PostMapping("/{id}/purchase")
    public ResponseEntity<?> purchaseCourse(@PathVariable Long id) {

        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        return ResponseEntity.ok(
                new AppResponse(
                        "Course purchased successfully",
                        200,
                        courseService.purchaseCourse(id, username)
                )
        );
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<?> getTasksInCourse(@PathVariable Long id) {

        return ResponseEntity.ok(
                new AppResponse(
                        "List of tasks in course",
                        200,
                        courseService.getCourseTasks(id)
                )
        );
    }

    @PutMapping("/{id}/tasks")
    public ResponseEntity<?> createTaskInCourse(@PathVariable Long id,
                                                @Valid @RequestBody TaskDTO taskDTO) {

        String teacherLogin = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        return ResponseEntity.ok(
                new AppResponse(
                        "Task created successfully",
                        201,
                        courseService.createCourseTask(id, taskDTO, teacherLogin)
                )
        );
    }

    @GetMapping("/{id}/tasks/{taskId}")
    public ResponseEntity<?> getTaskInCourse(@PathVariable Long id,
                                             @PathVariable Long taskId) {

        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        return ResponseEntity.ok(
                new AppResponse(
                        "Task found successfully",
                        200,
                        courseService.getCourseTask(id, taskId, username)
                )
        );
    }

    @PatchMapping("/{id}/tasks/{taskId}")
    public ResponseEntity<?> updateTaskInCourse(@PathVariable Long id,
                                                @PathVariable Long taskId,
                                                @Valid @RequestBody TaskDTO taskDTO) {

        String teacherLogin = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        return ResponseEntity.ok(
                new AppResponse(
                        "Task updated successfully",
                        200,
                        courseService.updateCourseTask(id, taskId, taskDTO, teacherLogin)
                )
        );
    }
}
