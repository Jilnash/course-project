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

    public Task createTask(TaskDTO taskDTO, String login) {

        Task task = new Task();

        task.setDescription(taskDTO.getDescription());
        task.setVideoLink(taskDTO.getVideoLink());
        task.setCourse(courseService.getCourse(taskDTO.getCourseId()));
        task.setCreatedBy(adminService.getAdmin(login));
        task.setPrerequisites(taskDTO.getPrerequisites().stream().map(this::getTask).toList());

        return taskRepo.save(task);
    }

    public Task getTask(Long id) {
        return taskRepo
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Task " + id + " not found"));
    }

    public boolean updateTask(Long id, TaskDTO taskDTO, String login) {

        Task task = getTask(id);

        task.setDescription(taskDTO.getDescription());
        task.setVideoLink(taskDTO.getVideoLink());
        task.setCourse(courseService.getCourse(taskDTO.getCourseId()));
        task.setLastUpdatedBy(adminService.getAdmin(login));
        task.setPrerequisites(taskDTO.getPrerequisites().stream().map(this::getTask).toList());

        taskRepo.save(task);
        return true;
    }
}
