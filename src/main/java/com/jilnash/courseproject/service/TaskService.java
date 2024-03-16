package com.jilnash.courseproject.service;

import com.jilnash.courseproject.dto.request.education.TaskDTO;
import com.jilnash.courseproject.model.education.Task;
import com.jilnash.courseproject.repo.education.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private AdminService adminService;

    @Autowired
    private CourseService courseService;

    public Task createTask(TaskDTO taskDTO) {

        Task task = new Task();

        task.setDescription(taskDTO.getDescription());
        task.setVideoLink(taskDTO.getVideoLink());
        task.setCourse(courseService.getCourse(taskDTO.getCourseId()));
        task.setCreatedBy(adminService.getAdmin(taskDTO.getAdminId()));

        return taskRepo.save(task);
    }

    public Task getTask(Long id) {
        return taskRepo
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Task not found"));
    }

    public boolean updateTask(Long id, TaskDTO taskDTO) {

        Task task = getTask(id);

        task.setDescription(taskDTO.getDescription());
        task.setVideoLink(taskDTO.getVideoLink());
        task.setCourse(courseService.getCourse(taskDTO.getCourseId()));
        task.setLastUpdatedBy(adminService.getAdmin(taskDTO.getAdminId()));

        taskRepo.save(task);
        return true;
    }
}
