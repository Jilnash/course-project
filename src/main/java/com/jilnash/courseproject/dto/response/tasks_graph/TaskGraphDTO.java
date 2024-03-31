package com.jilnash.courseproject.dto.response.tasks_graph;

import lombok.Data;

import java.util.List;

@Data
public class TaskGraphDTO {

    private List<TaskNodeDTO> nodes;
    private List<TaskLinksDTO> links;
}
