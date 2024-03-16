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

    public HwResponse getResponseById(Long id) {
        return responseRepo
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Response not found"));
    }

    public boolean createResponseToHomework(Long id, HwResponseDTO responseDTO) {

        Homework homework = homeworkService.getHomeworkById(id);

        if (homework.getChecked())
            throw new HomeworkAlreadyCheckedException("Homework already checked");

        homeworkService.checkHomework(homework);

        HwResponse hwResponse = new HwResponse();

        hwResponse.setHomework(homework);
        hwResponse.setTeacher(
                teacherService.getTeacherById(responseDTO.getTeacherId())
        );

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

        return true;
    }
}
