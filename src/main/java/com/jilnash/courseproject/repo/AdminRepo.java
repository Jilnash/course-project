package com.jilnash.courseproject.repo;

import com.jilnash.courseproject.model.participants.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Long> {
}
