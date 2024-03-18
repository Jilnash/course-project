package com.jilnash.courseproject.controller;

import com.jilnash.courseproject.dto.response.AppException;
import com.jilnash.courseproject.exception.HomeworkAlreadyCheckedException;
import com.jilnash.courseproject.exception.HomeworkFrequentPostingException;
import com.jilnash.courseproject.exception.StudentCourseAccessException;
import com.jilnash.courseproject.exception.TaskAlreadyCompletedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(
                new AppException(
                        ex.getBindingResult().getFieldError().getDefaultMessage(),
                        HttpStatus.BAD_REQUEST.value())
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(
                new AppException(
                        ex.getMessage(),
                        HttpStatus.BAD_REQUEST.value())
        );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UsernameNotFoundException ex) {
        return ResponseEntity.badRequest().body(
                new AppException(
                        ex.getMessage(),
                        HttpStatus.BAD_REQUEST.value())
        );
    }

    @ExceptionHandler(HomeworkAlreadyCheckedException.class)
    public ResponseEntity<?> handleHomeworkAlreadyCheckedException(HomeworkAlreadyCheckedException ex) {
        return ResponseEntity.badRequest().body(
                new AppException(
                        ex.getMessage(),
                        HttpStatus.BAD_REQUEST.value()
                )
        );
    }

    @ExceptionHandler(HomeworkFrequentPostingException.class)
    public ResponseEntity<?> handleHomeworkFrequentPostingException(HomeworkFrequentPostingException ex) {
        return ResponseEntity.badRequest().body(
                new AppException(
                        ex.getMessage(),
                        HttpStatus.BAD_REQUEST.value()
                )
        );
    }

    @ExceptionHandler(StudentCourseAccessException.class)
    public ResponseEntity<?> handleStudentCourseNoAccessException(StudentCourseAccessException ex) {
        return ResponseEntity.badRequest().body(
                new AppException(
                        ex.getMessage(),
                        HttpStatus.BAD_REQUEST.value()
                )
        );
    }

    @ExceptionHandler(TaskAlreadyCompletedException.class)
    public ResponseEntity<?> handleTaskAlreadyCompletedException(TaskAlreadyCompletedException ex) {
        return ResponseEntity.badRequest().body(
                new AppException(
                        ex.getMessage(),
                        HttpStatus.BAD_REQUEST.value()
                )
        );
    }
}
