package com.jilnash.courseproject.service;

import com.jilnash.courseproject.dto.request.education.CourseDTO;
import com.jilnash.courseproject.dto.request.education.TaskDTO;
import com.jilnash.courseproject.exception.StudentCourseAccessException;
import com.jilnash.courseproject.model.education.Course;
import com.jilnash.courseproject.model.education.Task;
import com.jilnash.courseproject.repo.education.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private AdminService adminService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

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
        course.setCreatedBy(adminService.getAdmin(courseDTO.getAdminId()));

        return courseRepo.save(course);
    }

    public boolean updateCourse(Long id, CourseDTO courseDTO) {

        Course course = getCourse(id);

        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());
        course.setDuration(courseDTO.getDuration());
        course.setUpdatedBy(adminService.getAdmin(courseDTO.getAdminId()));

        courseRepo.save(course);
        return true;
    }

    public List<Task> getCourseTasks(Long courseId, String username) {

        Course course = getCourse(courseId);

        if (userService.hasAccessToCourse(username, courseId))
            throw new StudentCourseAccessException("You don't have access to this course");

        return course.getTasks();
    }

    public Task getCourseTask(Long courseId, Long taskId, String username) {

        if (userService.hasAccessToCourse(username, courseId))
            throw new StudentCourseAccessException("You don't have access to this course");

        return getCourse(courseId)
                .getTasks()
                .stream()
                .filter(task -> task.getId().equals(taskId))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("Task not found"));
    }

    public Task createCourseTask(Long courseId, TaskDTO taskDTO) {

        Course course = getCourse(courseId);

        course.setUpdatedBy(adminService.getAdmin(taskDTO.getAdminId()));

        courseRepo.save(course);

        return taskService.createTask(taskDTO);
    }

    public boolean updateCourseTask(Long taskId, TaskDTO taskDTO) {

        return taskService.updateTask(taskId, taskDTO);
    }
}
