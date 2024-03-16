package com.jilnash.courseproject.service;

import com.jilnash.courseproject.dto.request.education.TaskDTO;
import com.jilnash.courseproject.model.education.Task;
import com.jilnash.courseproject.repo.participants.AdminRepo;
import com.jilnash.courseproject.repo.education.CourseRepo;
import com.jilnash.courseproject.repo.education.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private CourseRepo courseRepo;

    public Task createTask(TaskDTO taskDTO) {

        Task task = new Task();

        task.setDescription(taskDTO.getDescription());
        task.setVideoLink(taskDTO.getVideoLink());
        task.setCourse(
                courseRepo
                        .findById(taskDTO.getCourseId())
                        .orElseThrow(() -> new UsernameNotFoundException("Course not found"))
        );
        task.setCreatedBy(
                adminRepo
                        .findById(taskDTO.getAdminId())
                        .orElseThrow(() -> new UsernameNotFoundException("Admin not found"))
        );

        return taskRepo.save(task);
    }

    public Task getTask(Long id) {
        return taskRepo
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Task not found"));
    }

    public boolean updateTask(Long id, TaskDTO taskDTO) {

        Task task = taskRepo
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Task not found"));

        task.setDescription(taskDTO.getDescription());
        task.setVideoLink(taskDTO.getVideoLink());
        task.setCourse(
                courseRepo
                        .findById(taskDTO.getCourseId())
                        .orElseThrow(() -> new UsernameNotFoundException("Course not found"))
        );
        task.setLastUpdatedBy(
                adminRepo
                        .findById(taskDTO.getAdminId())
                        .orElseThrow(() -> new UsernameNotFoundException("Admin not found"))
        );

        taskRepo.save(task);
        return true;
    }
}
