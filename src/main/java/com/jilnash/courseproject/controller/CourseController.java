package com.jilnash.courseproject.controller;

import com.jilnash.courseproject.dto.request.education.CourseDTO;
import com.jilnash.courseproject.dto.request.education.TaskDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @GetMapping
    public String getAllCourses() {
        return "All courses";
    }

    @PutMapping
    public String createCourse(@Valid @RequestBody CourseDTO courseDTO) {
        System.out.println(courseDTO);
        return "Course created";
    }

    @GetMapping("/{id}")
    public String getCourse(@PathVariable Long id) {
        return "Course " + id;
    }

    @PatchMapping("/{id}")
    public String updateCourse(@PathVariable Long id,
                               @Valid @RequestBody CourseDTO courseDTO) {
        System.out.println(courseDTO);
        return "Course " + id + " updated";
    }

    @PostMapping("/{id}/purchase")
    public String purchaseCourse(@PathVariable Long id) {
        return "Course " + id + " purchased";
    }

    @GetMapping("/{id}/tasks")
    public String getTasksInCourse(@PathVariable Long id) {
        return "Tasks for course " + id;
    }

    @PutMapping("/{id}/tasks")
    public String createTaskInCourse(@PathVariable Long id,
                                     @Valid @RequestBody TaskDTO taskDTO) {
        System.out.println(taskDTO);
        return "Task for course " + id + " created";
    }

    @GetMapping("/{id}/tasks/{taskId}")
    public String getTaskInCourse(@PathVariable Long id,
                                  @PathVariable Long taskId) {
        return "Task " + taskId + " for course " + id;
    }

    @PatchMapping("/{id}/tasks/{taskId}")
    public String updateTaskInCourse(@PathVariable Long id,
                                     @PathVariable Long taskId,
                                     @RequestBody TaskDTO taskDTO) {
        System.out.println(taskDTO);
        return "Task " + taskId + " for course " + id + " updated";
    }
}
