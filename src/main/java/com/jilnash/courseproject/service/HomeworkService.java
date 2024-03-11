package com.jilnash.courseproject.service;

import com.jilnash.courseproject.dto.request.education.HomeworkDTO;
import com.jilnash.courseproject.model.education.Homework;
import com.jilnash.courseproject.repo.HomeworkRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeworkService {

    @Autowired
    private HomeworkRepo homeworkRepo;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private S3Service s3Service;

    public List<Homework> getHomeworks() {
        return homeworkRepo.findAll();
    }

    public Homework createHomework(HomeworkDTO homeworkDTO) throws Exception {

        Homework homework = new Homework();

        homework.setStudent(
                studentService.getStudent(homeworkDTO.getStudentId())
        );
        homework.setTask(
                taskService.getTask(homeworkDTO.getTaskId())
        );

        Integer attempts = homeworkRepo.countByStudentIdAndTaskId(homeworkDTO.getStudentId(), homeworkDTO.getTaskId());

        String audioLink = String.format(
                "student%d-task%d-audio-%d",
                homeworkDTO.getStudentId(),
                homeworkDTO.getTaskId(),
                attempts + 1
        );

        String videoLink = String.format(
                "student%d-task%d-video-%d",
                homeworkDTO.getStudentId(),
                homeworkDTO.getTaskId(),
                attempts + 1
        );

        s3Service.putFileToS3(homeworkDTO.getAudio(), audioLink);
        s3Service.putFileToS3(homeworkDTO.getVideo(), videoLink);

        homework.setAudioLink(audioLink);
        homework.setVideoLink(videoLink);

        return homeworkRepo.save(homework);
    }

    public Resource getHomeworkById(Long id) {

        Homework homework = homeworkRepo
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Homework not found"));

        return s3Service.getFileFromS3(homework.getAudioLink(), "audio.mpeg");
    }
}
