package com.divyanshu.teamtaskmanager.dto.task;

import lombok.Data;

@Data
public class TaskUpdateRequest {

    private String title;
    private String description;
    private String deadline;
    private Integer progress;
}