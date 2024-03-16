package com.jilnash.courseproject.repo.education;

import com.jilnash.courseproject.model.education.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {
}
