package com.jilnash.courseproject.repo;

import com.jilnash.courseproject.model.education.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface HomeworkRepo extends JpaRepository<Homework, Long> {

    Integer countByStudentIdAndTaskId(Long studentId, Long taskId);

    Optional<Homework> findByTaskIdAndStudentIdAndCreatedAtAfter(Long taskId, Long studentId, Date date);
}
