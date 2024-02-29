package com.jilnash.courseproject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppException {
    private String message;
    private int code;
}
