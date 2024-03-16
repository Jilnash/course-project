package com.jilnash.courseproject.repo.education;

import com.jilnash.courseproject.model.education.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HomeworkRepo extends JpaRepository<Homework, Long>, JpaSpecificationExecutor<Homework> {

    Integer countByStudentIdAndTaskId(Long studentId, Long taskId);

    List<Homework> findAllByTaskIdAndStudentIdAndCreatedAtAfter(Long taskId, Long studentId, Date date);
}
