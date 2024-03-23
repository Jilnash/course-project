package com.jilnash.courseproject.repo.participants;

import com.jilnash.courseproject.model.participants.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepo extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findByUserLogin(String login);

    Boolean existsByUserLogin(String login);
}
