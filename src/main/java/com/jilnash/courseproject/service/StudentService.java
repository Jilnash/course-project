package com.jilnash.courseproject.service;

import com.jilnash.courseproject.dto.request.participants.StudentDTO;
import com.jilnash.courseproject.model.participants.Student;
import com.jilnash.courseproject.model.participants.User;
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

    public List<Student> getStudents() {
        return studentRepo.findAll();
    }

    public Student getStudent(Long id) {
        return studentRepo
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Student not found"));
    }

    public Student getStudent(User user) {
        return studentRepo
                .getByUser(user)
                .orElseThrow(() -> new UsernameNotFoundException("Student not found"));
    }

    public Student createStudent(StudentDTO studentDTO) {

        Student student = new Student();

        student.setName(studentDTO.getName());
        student.setSurname(studentDTO.getSurname());
        student.setSkype(studentDTO.getSkype());

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

    public boolean hasAccessToCourse(Long studentId, Long courseId) {
        return studentCourseAccessRepo.existsByStudentIdAndCourseIdAndEndDateAfter(
                studentId,
                courseId,
                Date.valueOf(LocalDate.now())
        );
    }
}
