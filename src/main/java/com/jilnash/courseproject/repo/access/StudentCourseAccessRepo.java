package com.jilnash.courseproject.repo.access;

import com.jilnash.courseproject.model.access.StudentCourseAccess;
import com.jilnash.courseproject.model.education.Course;
import com.jilnash.courseproject.model.participants.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface StudentCourseAccessRepo extends JpaRepository<StudentCourseAccess, Long> {

    boolean existsByStudentAndCourseAndEndDateAfter(Student student, Course course, Date currentDate);
}
