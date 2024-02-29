package com.jilnash.courseproject.repo;

import com.jilnash.courseproject.model.participants.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {
}
