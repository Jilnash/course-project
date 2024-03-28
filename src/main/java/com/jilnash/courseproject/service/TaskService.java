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

    public Task getTask(Long taskId) {
        return taskRepo
                .findById(taskId)
                .orElseThrow(() -> new UsernameNotFoundException("There is no task with id: " + taskId));
    }

    public Task getTask(Long courseId, Long taskId) {
        return taskRepo
                .findByIdAndCourseId(taskId, courseId)
                .orElseThrow(() -> new UsernameNotFoundException("There is no task with id: " + taskId + " in course with id: " + courseId));
    }

    public boolean updateTask(Course course, Long taskId, TaskDTO taskDTO) {

        Task task = getTask(course.getId(), taskId);

        setCommonFields(task, taskDTO);

        task.getTaskAspectLevels().clear();
        task.getTaskAspectLevels().addAll(getTaskAspectLevels(task, taskDTO));

        taskRepo.save(task);
        return true;
    }

    private void setCommonFields(Task task, TaskDTO taskDTO) {

        checkAudioAndVideoNotRequired(taskDTO);

        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setVideoLink(taskDTO.getVideoLink());
        task.setAudioRequired(taskDTO.getAudioRequired());
        task.setVideoRequired(taskDTO.getVideoRequired());
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

    private void checkAudioAndVideoNotRequired(TaskDTO taskDTO) {
        if (!taskDTO.getAudioRequired() && !taskDTO.getVideoRequired())
            throw new IllegalArgumentException("At least one of audio or video must be required");
    }
}
