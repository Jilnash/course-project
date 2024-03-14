package com.jilnash.courseproject.service;

import com.jilnash.courseproject.dto.request.education.HomeworkDTO;
import com.jilnash.courseproject.exception.HomeworkFrequentPostingException;
import com.jilnash.courseproject.model.education.Homework;
import com.jilnash.courseproject.repo.HomeworkRepo;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
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

    public List<Homework> getHomeworks(Long studentId, Long taskId, Boolean checked, Date createdAfter) {

        Specification<Homework> spec = (root, query, cb) -> {
            Predicate p = cb.conjunction();

            if (studentId != null)
                p = cb.and(p, cb.equal(root.get("student").get("id"), studentId));

            if (taskId != null)
                p = cb.and(p, cb.equal(root.get("task").get("id"), taskId));

            if (checked != null)
                p = cb.and(p, cb.equal(root.get("checked"), checked));

            if (createdAfter != null)
                p = cb.and(p, cb.greaterThanOrEqualTo(root.get("createdAt"), createdAfter));

            return p;
        };

        return homeworkRepo.findAll(spec);
    }

    public Homework createHomework(HomeworkDTO homeworkDTO) throws Exception {

        Date weekBefore = Date.valueOf(LocalDate.now().minusWeeks(1));

        if (
                !homeworkRepo.findAllByTaskIdAndStudentIdAndCreatedAtAfter(
                        homeworkDTO.getTaskId(),
                        homeworkDTO.getStudentId(),
                        weekBefore
                ).isEmpty()
        )
            throw new HomeworkFrequentPostingException("Homework must be posted once a week");

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

    public Resource getHomeworkAudioById(Long id) {

        Homework homework = homeworkRepo
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Homework not found"));

        return s3Service.getFileFromS3(homework.getAudioLink(), "audio.mpeg");
    }

    public Homework getHomeworkById(Long id) {
        return homeworkRepo
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Homework not found"));
    }

    public void checkHomework(Homework hw) {
        hw.setChecked(true);
        homeworkRepo.save(hw);
    }
}
