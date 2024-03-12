package com.jilnash.courseproject.exception;

public class HomeworkAlreadyCheckedException extends RuntimeException {

    public HomeworkAlreadyCheckedException(String message) {
        super(message);
    }
}
