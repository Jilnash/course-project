package com.jilnash.courseproject.service;

import com.jilnash.courseproject.dto.request.education.CourseDTO;
import com.jilnash.courseproject.dto.request.education.TaskDTO;
import com.jilnash.courseproject.dto.response.tasks_graph.TaskGraphDTO;
import com.jilnash.courseproject.dto.response.tasks_graph.TaskLinksDTO;
import com.jilnash.courseproject.dto.response.tasks_graph.TaskNodeDTO;
import com.jilnash.courseproject.exception.IncompletePrerequisitesException;
import com.jilnash.courseproject.exception.NoTaskWIthNoPrerequisitesException;
import com.jilnash.courseproject.exception.StudentCourseAccessException;
import com.jilnash.courseproject.exception.TeacherCourseAccessException;
import com.jilnash.courseproject.model.access.StudentCourseAccess;
import com.jilnash.courseproject.model.education.Course;
import com.jilnash.courseproject.model.education.Task;
import com.jilnash.courseproject.model.participants.Student;
import com.jilnash.courseproject.model.participants.Teacher;
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
    private TeacherService teacherService;

    @Autowired
    private TaskService taskService;

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
                .orElseThrow(() -> new UsernameNotFoundException("Course with id: " + id + " not found"));
    }

    public Course createCourse(CourseDTO courseDTO, String login) {

        Course course = new Course();

        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());
        course.setDuration(courseDTO.getDuration());
        course.setAuthor(teacherService.getTeacher(login));
        course.setHwPostingDayInterval(courseDTO.getHwPostingDayInterval());

        return courseRepo.save(course);
    }

    public boolean updateCourse(Long id, CourseDTO courseDTO, String login) {

        Course course = getCourse(id);
        Teacher teacher = teacherService.getTeacher(login);

        if (!teacherService.checkTeacherCourseAccess(course, teacher))
            throw new TeacherCourseAccessException("You don't have update access to this course");

        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());
        course.setDuration(courseDTO.getDuration());
        course.setHwPostingDayInterval(courseDTO.getHwPostingDayInterval());

        courseRepo.save(course);
        return true;
    }

    public boolean purchaseCourse(Long courseId, String username) {

        Course course = getCourse(courseId);
        Student student = studentService.getStudent(username);

        if (studentService.checkStudentCourseAccess(student, course))
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

    public TaskGraphDTO getCourseTasks(Long courseId) {

        Course course = getCourse(courseId);
        List<Task> tasks = course.getTasks();

        TaskGraphDTO taskGraph = new TaskGraphDTO();

        taskGraph.setNodes(tasks.stream().map(
                task -> new TaskNodeDTO(task.getId(), task.getTitle())).toList()
        );

        taskGraph.setLinks(
                tasks.stream().flatMap(
                        task -> task.getPrerequisites().stream().map(
                                prerequisite -> new TaskLinksDTO(prerequisite.getId(), task.getId())
                        )
                ).toList()
        );

        return taskGraph;
    }

    public Task getCourseTask(Long courseId, Long taskId, String username) {

        Course course = getCourse(courseId);

        if (teacherService.exists(username)) {

            if (!teacherService.checkTeacherCourseAccess(course, teacherService.getTeacher(username)))
                throw new TeacherCourseAccessException("You don't have access to this course");

            return taskService.getTask(courseId, taskId);
        }

        if (studentService.exists(username)) {
            Student student = studentService.getStudent(username);

            if (!studentService.checkStudentCourseAccess(student, course))
                throw new StudentCourseAccessException("You don't have access to this course");

            Task task = taskService.getTask(courseId, taskId);

            if (!studentService.checkStudentTaskPrerequisitesCompleted(student, task))
                throw new IncompletePrerequisitesException("You should complete all prerequisites first");

            return task;
        }

        return taskService.getTask(courseId, taskId);
    }

    public Task createCourseTask(Long courseId, TaskDTO taskDTO, String login) {

        Teacher teacher = teacherService.getTeacher(login);
        Course course = getCourse(courseId);

        if (!teacherService.checkTeacherCourseAccess(course, teacher))
            throw new TeacherCourseAccessException("You don't have access to this course");

        if (course.getTasks().isEmpty() && !taskDTO.getPrerequisites().isEmpty())
            throw new NoTaskWIthNoPrerequisitesException("First task should have no prerequisites");

        return taskService.createTask(course, taskDTO, teacher);
    }

    public boolean updateCourseTask(Long courseId, Long taskId, TaskDTO taskDTO, String login) {

        Course course = getCourse(courseId);

        if (!teacherService.checkTeacherCourseAccess(course, teacherService.getTeacher(login)))
            throw new TeacherCourseAccessException("You don't have access to this course");

        if (
                !taskDTO.getPrerequisites().isEmpty() &&
                course.getTasks().stream().noneMatch(task -> task.getPrerequisites().isEmpty())
        )
            throw new NoTaskWIthNoPrerequisitesException("There should be at least one task with no prerequisites");

        return taskService.updateTask(course, taskId, taskDTO);
    }
}
