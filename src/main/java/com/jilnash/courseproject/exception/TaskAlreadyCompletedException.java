package com.jilnash.courseproject.exception;

public class TaskAlreadyCompletedException extends RuntimeException {

    public TaskAlreadyCompletedException(String message) {
        super(message);
    }
}
