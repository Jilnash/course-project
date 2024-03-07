package com.jilnash.courseproject.service;

import com.jilnash.courseproject.dto.request.education.CourseDTO;
import com.jilnash.courseproject.dto.request.education.TaskDTO;
import com.jilnash.courseproject.model.education.Course;
import com.jilnash.courseproject.model.education.Task;
import com.jilnash.courseproject.repo.AdminRepo;
import com.jilnash.courseproject.repo.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private TaskService taskService;

    public List<Course> getCourses() {
        return courseRepo.findAll();
    }

    public Course getCourse(Long id) {
        return courseRepo
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Course not found"));
    }

    public Course createCourse(CourseDTO courseDTO) {

        Course course = new Course();

        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());
        course.setDuration(courseDTO.getDuration());
        course.setCreatedBy(
                adminRepo
                        .findById(courseDTO.getAdminId())
                        .orElseThrow(() -> new UsernameNotFoundException("Admin not found"))
        );

        return courseRepo.save(course);
    }

    public boolean updateCourse(Long id, CourseDTO courseDTO) {

        Course course = courseRepo
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Course not found"));

        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());
        course.setDuration(courseDTO.getDuration());
        course.setUpdatedBy(
                adminRepo
                        .findById(courseDTO.getAdminId())
                        .orElseThrow(() -> new UsernameNotFoundException("Admin not found"))
        );

        courseRepo.save(course);
        return true;
    }

    public List<Task> getCourseTasks(Long id) {
        return courseRepo
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Course not found"))
                .getTasks();
    }

    public Task getCourseTask(Long courseId, Long taskId) {
        return courseRepo
                .findById(courseId)
                .orElseThrow(() -> new UsernameNotFoundException("Course not found"))
                .getTasks()
                .stream()
                .filter(task -> task.getId().equals(taskId))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("Task not found"));
    }

    public Task createCourseTask(Long courseId, TaskDTO taskDTO) {

        Course course = courseRepo
                .findById(courseId)
                .orElseThrow(() -> new UsernameNotFoundException("Course not found"));

        course.setUpdatedBy(
                adminRepo
                        .findById(taskDTO.getAdminId())
                        .orElseThrow(() -> new UsernameNotFoundException("Admin not found"))
        );

        courseRepo.save(course);

        return taskService.createTask(taskDTO);
    }

    public boolean updateCourseTask(Long taskId, TaskDTO taskDTO) {

        return taskService.updateTask(taskId, taskDTO);
    }
}
