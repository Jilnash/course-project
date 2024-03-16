package com.jilnash.courseproject.repo.education;

import com.jilnash.courseproject.model.education.HwResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseRepo extends JpaRepository<HwResponse, Long> {
}
