package com.jilnash.courseproject.service;

import com.jilnash.courseproject.dto.request.education.HwResponseDTO;
import com.jilnash.courseproject.exception.HomeworkAlreadyCheckedException;
import com.jilnash.courseproject.model.education.Comment;
import com.jilnash.courseproject.model.education.Homework;
import com.jilnash.courseproject.model.education.HwResponse;
import com.jilnash.courseproject.model.education.TimeRange;
import com.jilnash.courseproject.repo.education.CommentRepo;
import com.jilnash.courseproject.repo.education.ResponseRepo;
import com.jilnash.courseproject.repo.education.TimeRangeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class HwResponseService {

    @Autowired
    private HomeworkService homeworkService;

    @Autowired
    private ResponseRepo responseRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private TimeRangeRepo timeRangeRepo;

    @Autowired
    private TeacherService teacherService;

    public HwResponse getResponse(Long hwId) {
        return responseRepo
                .findByHomeworkId(hwId)
                .orElseThrow(() -> new UsernameNotFoundException("Response not found"));
    }

    public boolean createResponseToHomework(Long id, HwResponseDTO responseDTO, String teacherLogin) {

        Homework homework = homeworkService.getHomeworkById(id);

        if (homework.getChecked())
            throw new HomeworkAlreadyCheckedException("Homework already checked");

        HwResponse hwResponse = new HwResponse();

        hwResponse.setTeacher(teacherService.getTeacher(teacherLogin));
        hwResponse.setHomework(homework);

        final HwResponse savedResponse = responseRepo.save(hwResponse);

        responseDTO
                .getComments()
                .forEach(commentDTO -> {
                            TimeRange timeRange = timeRangeRepo.save(
                                    new TimeRange(commentDTO.getStart(), commentDTO.getEnd())
                            );

                            commentRepo.save(
                                    new Comment(
                                            commentDTO.getNote(),
                                            timeRange,
                                            savedResponse
                                    )
                            );
                        }
                );

        homeworkService.checkHomework(homework);

        return true;
    }
}
