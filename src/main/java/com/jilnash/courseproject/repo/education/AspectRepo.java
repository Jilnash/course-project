package com.jilnash.courseproject.repo.education;

import com.jilnash.courseproject.model.education.Aspect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AspectRepo extends JpaRepository<Aspect, Long> {
}
