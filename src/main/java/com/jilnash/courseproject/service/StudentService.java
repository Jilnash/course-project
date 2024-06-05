package com.jilnash.courseproject.service;

import com.jilnash.courseproject.dto.request.participants.StudentDTO;
import com.jilnash.courseproject.model.education.Course;
import com.jilnash.courseproject.model.education.Task;
import com.jilnash.courseproject.model.participants.Student;
import com.jilnash.courseproject.repo.access.StudentCourseAccessRepo;
import com.jilnash.courseproject.repo.participants.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private StudentCourseAccessRepo studentCourseAccessRepo;

    @Autowired
    private UserService userService;

    public List<Student> getStudents() {
        return studentRepo.findAll();
    }

    public Student getStudent(Long id) {
        return studentRepo
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Student with id: " + id + " not found"));
    }

    public Student getStudent(String login) {
        return studentRepo
                .getByUserLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Student with login: " + login + " not found"));
    }

    public Boolean exists(String login) {

        return studentRepo.existsByUserLogin(login);
    }

    public Student createStudent(StudentDTO studentDTO) {

        Student student = new Student();

        student.setName(studentDTO.getName());
        student.setSurname(studentDTO.getSurname());
        student.setSkype(studentDTO.getSkype());
        student.setUser(userService.getUser(studentDTO.getUserLogin()));

        return studentRepo.save(student);
    }

    public boolean updateStudent(Long id, StudentDTO studentDTO) {

        Student student = getStudent(id);

        student.setName(studentDTO.getName());
        student.setSurname(studentDTO.getSurname());
        student.setSkype(studentDTO.getSkype());

        studentRepo.save(student);

        return true;
    }

    public boolean checkStudentCourseAccess(Student student, Course course) {
        return studentCourseAccessRepo.existsByStudentAndCourseAndEndDateAfter(
                student,
                course,
                Date.valueOf(LocalDate.now())
        );
    }

    public boolean checkStudentTaskPrerequisitesCompleted(Student student, Task task) {
        return student.getCompletedTasks().containsAll(task.getPrerequisites());
    }

    public void completeTask(Student student, Task task) {

        student.getCompletedTasks().add(task);

        studentRepo.save(student);
    }
}
