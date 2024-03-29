package com.jilnash.courseproject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppResponse {
    private String message;
    private int status;
    private Object data;
}
