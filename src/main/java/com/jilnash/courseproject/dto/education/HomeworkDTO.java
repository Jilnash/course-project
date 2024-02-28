package com.jilnash.courseproject.dto.education;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class HomeworkDTO {
    private Long studentId;
    private Long taskId;
    private MultipartFile audio;
    private MultipartFile video;
}
