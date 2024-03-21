package com.jilnash.courseproject.service;

import com.jilnash.courseproject.dto.request.education.HomeworkDTO;
import com.jilnash.courseproject.exception.HomeworkFrequentPostingException;
import com.jilnash.courseproject.exception.TaskAlreadyCompletedException;
import com.jilnash.courseproject.model.education.Homework;
import com.jilnash.courseproject.model.education.Task;
import com.jilnash.courseproject.model.participants.Student;
import com.jilnash.courseproject.repo.education.HomeworkRepo;
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

    public Homework createHomework(HomeworkDTO homeworkDTO, String studentLogin) throws Exception {

        Student student = studentService.getStudent(studentLogin);

        checkTaskCompletion(homeworkDTO, student);
        checkHomeworkPostingFrequency(homeworkDTO, student);

        Homework homework = new Homework();
        homework.setStudent(student);
        homework.setTask(taskService.getTask(homeworkDTO.getTaskId()));

        String audioLink = generateLink(homeworkDTO, student, "audio");
        String videoLink = generateLink(homeworkDTO, student, "video");

        s3Service.putFileToS3(homeworkDTO.getAudio(), audioLink);
        s3Service.putFileToS3(homeworkDTO.getVideo(), videoLink);

        homework.setAudioLink(audioLink);
        homework.setVideoLink(videoLink);

        return homeworkRepo.save(homework);
    }

    private void checkTaskCompletion(HomeworkDTO homeworkDTO, Student student) {
        if (student.getCompletedTasks()
                .stream()
                .map(Task::getId)
                .toList()
                .contains(homeworkDTO.getTaskId())
        )
            throw new TaskAlreadyCompletedException("Task already completed");
    }

    private void checkHomeworkPostingFrequency(HomeworkDTO homeworkDTO, Student student) {
        Date weekBefore = Date.valueOf(LocalDate.now().minusWeeks(1));
        if (!homeworkRepo.findAllByTaskIdAndStudentIdAndCreatedAtAfter(
                homeworkDTO.getTaskId(),
                student.getId(),
                weekBefore
        ).isEmpty())
            throw new HomeworkFrequentPostingException("Homework must be posted once a week");
    }

    private String generateLink(HomeworkDTO homeworkDTO, Student student, String fileType) {
        Integer attempts = homeworkRepo.countByStudentIdAndTaskId(student.getId(), homeworkDTO.getTaskId());
        return String.format("student%d-task%d-%s-%d", student.getId(), homeworkDTO.getTaskId(), fileType, attempts + 1);
    }

    public Resource getHomeworkAudio(Long id) {

        Homework homework = getHomework(id);

        return s3Service.getFileFromS3(homework.getAudioLink(), "audio.mpeg");
    }

    public Homework getHomework(Long id) {
        return homeworkRepo
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Homework with id: " + id + " not found"));
    }

    public void checkHomework(Homework hw) {
        hw.setChecked(true);
        homeworkRepo.save(hw);
    }
}
