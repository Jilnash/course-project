package com.jilnash.courseproject.dto.participants;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeacherDTO {
    private Long id;
    private String name;
    private String surname;
    private String description;
    private String photo;
    private String mediaLink;
}
