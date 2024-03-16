package com.jilnash.courseproject.repo.access;

import com.jilnash.courseproject.model.access.StudentCourseAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface StudentCourseAccessRepo extends JpaRepository<StudentCourseAccess, Long> {

    boolean existsByStudentIdAndCourseIdAndEndDateAfter(Long studentId, Long courseId, Date currentDate);
}
