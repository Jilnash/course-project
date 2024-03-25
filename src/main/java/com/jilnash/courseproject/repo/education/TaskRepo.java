package com.jilnash.courseproject.repo.education;

import com.jilnash.courseproject.model.education.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {

    Optional<Task> findByIdAndCourseId(Long id, Long courseId);
}
