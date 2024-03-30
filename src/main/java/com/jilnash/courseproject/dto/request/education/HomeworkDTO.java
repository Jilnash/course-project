package com.jilnash.courseproject.dto.request.education;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class HomeworkDTO {

    @NotNull(message = "Task id must not be null")
    @PositiveOrZero(message = "Task id must be positive")
    private Long taskId;

    private MultipartFile audio;

    private MultipartFile video;
}
