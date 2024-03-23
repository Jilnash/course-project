package com.jilnash.courseproject.repo.participants;

import com.jilnash.courseproject.model.participants.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {

    Boolean existsByUserLogin(String login);

    Optional<Student> getByUserLogin(String login);
}
