package com.jilnash.courseproject.repo.education;

import com.jilnash.courseproject.model.education.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {
}
