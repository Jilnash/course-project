package com.jilnash.courseproject.service;

import com.jilnash.courseproject.dto.request.participants.StudentDTO;
import com.jilnash.courseproject.model.education.Course;
import com.jilnash.courseproject.model.participants.Student;
import com.jilnash.courseproject.repo.CourseRepo;
import com.jilnash.courseproject.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private CourseRepo courseRepo;

    public List<Student> getStudents() {
        return studentRepo.findAll();
    }

    public Student getStudent(Long id) {
        return studentRepo
                .findById(id)
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

        Student student = studentRepo
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Student not found"));

        student.setName(studentDTO.getName());
        student.setSurname(studentDTO.getSurname());
        student.setSkype(studentDTO.getSkype());

        studentRepo.save(student);

        return true;
    }

    public boolean purchaseCourse(Long studentId, Long courseId) {
        Student student = studentRepo
                .findById(studentId)
                .orElseThrow(() -> new UsernameNotFoundException("Student not found"));

        Course course = courseRepo
                .findById(courseId)
                .orElseThrow(() -> new UsernameNotFoundException("Course not found"));

        student.getCourses().add(course);

        studentRepo.save(student);

        return true;
    }
}
