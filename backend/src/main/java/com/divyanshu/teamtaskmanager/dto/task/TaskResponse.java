package com.divyanshu.teamtaskmanager.dto.task;

import com.divyanshu.teamtaskmanager.entity.TaskCategory;
import com.divyanshu.teamtaskmanager.entity.TaskPriority;
import com.divyanshu.teamtaskmanager.entity.TaskStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponse {

    private Long id;
    private String title;
    private String description;

    private TaskPriority priority;
    private TaskStatus status;
    private TaskCategory category;

    private Integer estimatedHours;
    private String notes;

    private String dueDate;
    private String assignedTo;
    private String projectName;

    private Integer progress;
    private boolean overdue;
}