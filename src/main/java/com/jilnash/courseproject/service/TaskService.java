package com.jilnash.courseproject.service;

import com.jilnash.courseproject.dto.request.education.TaskDTO;
import com.jilnash.courseproject.model.education.Course;
import com.jilnash.courseproject.model.education.Task;
import com.jilnash.courseproject.model.education.TaskAspectLevel;
import com.jilnash.courseproject.model.participants.Teacher;
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
    private AspectService aspectService;

    public Task createTask(Course course, TaskDTO taskDTO, Teacher teacher) {

        Task task = new Task();

        task.setAuthor(teacher);
        task.setCourse(course);

        setCommonFields(task, taskDTO);

        task.setTaskAspectLevels(getTaskAspectLevels(task, taskDTO));

        return taskRepo.save(task);
    }

    public Task getTask(Long id) {
        return taskRepo
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Task " + id + " not found"));
    }

    public boolean updateTask(Long taskId, TaskDTO taskDTO) {

        Task task = getTask(taskId);

        setCommonFields(task, taskDTO);

        task.getTaskAspectLevels().clear();
        task.getTaskAspectLevels().addAll(getTaskAspectLevels(task, taskDTO));

        taskRepo.save(task);
        return true;
    }

    private void setCommonFields(Task task, TaskDTO taskDTO) {
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setVideoLink(taskDTO.getVideoLink());
        task.setPrerequisites(taskDTO.getPrerequisites().stream().map(this::getTask).collect(Collectors.toList()));
    }

    private List<TaskAspectLevel> getTaskAspectLevels(Task task, TaskDTO taskDTO) {
        return taskDTO.getTaskAspectLevels().stream()
                .map(dto ->
                        new TaskAspectLevel(
                                aspectService.getAspect(dto.getAspectId()),
                                task,
                                dto.getLevel()
                        )
                ).toList();
    }
}
