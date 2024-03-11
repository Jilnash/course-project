package com.jilnash.courseproject.service;

import com.jilnash.courseproject.dto.request.education.HwResponseDTO;
import com.jilnash.courseproject.model.education.Comment;
import com.jilnash.courseproject.model.education.Homework;
import com.jilnash.courseproject.model.education.HwResponse;
import com.jilnash.courseproject.model.education.TimeRange;
import com.jilnash.courseproject.repo.CommentRepo;
import com.jilnash.courseproject.repo.HomeworkRepo;
import com.jilnash.courseproject.repo.ResponseRepo;
import com.jilnash.courseproject.repo.TimeRangeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class HwResponseService {

    @Autowired
    private HomeworkRepo homeworkRepo;

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

        Homework homework = homeworkRepo
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Homework not found"));

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
