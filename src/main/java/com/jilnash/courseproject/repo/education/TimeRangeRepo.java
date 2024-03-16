package com.jilnash.courseproject.repo.education;

import com.jilnash.courseproject.model.education.TimeRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeRangeRepo extends JpaRepository<TimeRange, Long> {
}
