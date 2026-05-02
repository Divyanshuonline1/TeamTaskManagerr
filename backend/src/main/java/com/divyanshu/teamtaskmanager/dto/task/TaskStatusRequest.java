package com.divyanshu.teamtaskmanager.dto.task;

import com.divyanshu.teamtaskmanager.entity.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskStatusRequest {

    @NotNull
    private TaskStatus status;
}