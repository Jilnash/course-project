package com.jilnash.courseproject.repo;

import com.jilnash.courseproject.model.education.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeworkRepo extends JpaRepository<Homework, Long> {
}
