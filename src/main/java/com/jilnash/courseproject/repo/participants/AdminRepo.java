package com.jilnash.courseproject.repo.participants;

import com.jilnash.courseproject.model.participants.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Long> {

    Optional<Admin> findByUserLogin(String login);
}
