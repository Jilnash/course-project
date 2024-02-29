package com.jilnash.courseproject.service;

import com.jilnash.courseproject.repo.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepo teacherRepo;
}
