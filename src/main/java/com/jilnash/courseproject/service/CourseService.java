package com.jilnash.courseproject.service;

import com.jilnash.courseproject.dto.request.education.CourseDTO;
import com.jilnash.courseproject.dto.request.education.TaskDTO;
import com.jilnash.courseproject.exception.StudentCourseAccessException;
import com.jilnash.courseproject.model.access.StudentCourseAccess;
import com.jilnash.courseproject.model.education.Course;
import com.jilnash.courseproject.model.education.Task;
import com.jilnash.courseproject.model.participants.Student;
import com.jilnash.courseproject.repo.access.StudentCourseAccessRepo;
import com.jilnash.courseproject.repo.education.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
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

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentCourseAccessRepo studentCourseAccessRepo;

    public List<Course> getCourses() {
        return courseRepo.findAll();
    }

    public Course getCourse(Long id) {
        return courseRepo
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Course not found"));
    }

    public Course createCourse(CourseDTO courseDTO, String login) {

        Course course = new Course();

        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());
        course.setDuration(courseDTO.getDuration());
        course.setCreatedBy(adminService.getAdmin(login));

        return courseRepo.save(course);
    }

    public boolean updateCourse(Long id, CourseDTO courseDTO, String login) {

        Course course = getCourse(id);

        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());
        course.setDuration(courseDTO.getDuration());
        course.setUpdatedBy(adminService.getAdmin(login));

        courseRepo.save(course);
        return true;
    }

    public boolean purchaseCourse(Long courseId, String username) {

        Course course = getCourse(courseId);

        Student student = studentService.getStudent(userService.findByLogin(username).get());

        if (studentService.hasAccessToCourse(student.getId(), courseId))
            throw new StudentCourseAccessException("You already have access to this course");

        studentCourseAccessRepo.save(
                new StudentCourseAccess(
                        student,
                        course,
                        Date.valueOf(LocalDate.now().plusMonths(1))
                )
        );

        return true;
    }

    public List<Task> getCourseTasks(Long courseId, String username) {

        Course course = getCourse(courseId);

        if (!userService.hasAccessToCourse(username, courseId))
            throw new StudentCourseAccessException("You don't have access to this course");

        return course.getTasks();
    }

    public Task getCourseTask(Long courseId, Long taskId, String username) {

        Course course = getCourse(courseId);

        if (!userService.hasAccessToCourse(username, courseId))
            throw new StudentCourseAccessException("You don't have access to this course");

        return course
                .getTasks()
                .stream()
                .filter(task -> task.getId().equals(taskId))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("Task not found"));
    }

    public Task createCourseTask(Long courseId, TaskDTO taskDTO, String login) {

        Course course = getCourse(courseId);

        course.setUpdatedBy(adminService.getAdmin(login));

        courseRepo.save(course);

        return taskService.createTask(taskDTO, login);
    }

    public boolean updateCourseTask(Long taskId, TaskDTO taskDTO, String login) {

        return taskService.updateTask(taskId, taskDTO, login);
    }
}
