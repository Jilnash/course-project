package com.jilnash.courseproject.service;

import com.jilnash.courseproject.dto.request.education.TaskDTO;
import com.jilnash.courseproject.model.education.Task;
import com.jilnash.courseproject.model.education.TaskAspectLevel;
import com.jilnash.courseproject.repo.education.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private AdminService adminService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private AspectService aspectService;

    public Task createTask(TaskDTO taskDTO, String login) {

        Task task = new Task();

        task.setCreatedBy(adminService.getAdmin(login));

        setCommonFields(task, taskDTO);

        task.setTaskAspectLevels(getTaskAspectLevels(taskDTO));

        return taskRepo.save(task);
    }

    public Task getTask(Long id) {
        return taskRepo
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Task " + id + " not found"));
    }

    public boolean updateTask(Long id, TaskDTO taskDTO, String login) {

        Task task = getTask(id);

        task.setLastUpdatedBy(adminService.getAdmin(login));

        setCommonFields(task, taskDTO);

        task.getTaskAspectLevels().clear();
        task.getTaskAspectLevels().addAll(getTaskAspectLevels(taskDTO));

        taskRepo.save(task);
        return true;
    }

    private void setCommonFields(Task task, TaskDTO taskDTO) {
        task.setDescription(taskDTO.getDescription());
        task.setVideoLink(taskDTO.getVideoLink());
        task.setCourse(courseService.getCourse(taskDTO.getCourseId()));
        task.setPrerequisites(taskDTO.getPrerequisites().stream().map(this::getTask).collect(Collectors.toList()));
    }

    private List<TaskAspectLevel> getTaskAspectLevels(TaskDTO taskDTO) {
        return taskDTO.getTaskAspectLevels().stream()
                .map(dto ->
                        new TaskAspectLevel(
                                aspectService.getAspect(dto.getAspectId()),
                                null,
                                dto.getLevel()
                        )
                ).toList();
    }
}
