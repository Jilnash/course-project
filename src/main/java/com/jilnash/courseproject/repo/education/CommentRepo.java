package com.jilnash.courseproject.repo.education;

import com.jilnash.courseproject.model.education.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
}
