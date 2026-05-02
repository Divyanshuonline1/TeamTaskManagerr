package com.divyanshu.teamtaskmanager.dto.task;

import com.divyanshu.teamtaskmanager.entity.TaskCategory;
import com.divyanshu.teamtaskmanager.entity.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Priority is required")
    private TaskPriority priority;

    @NotNull(message = "Category is required")
    private TaskCategory category;

    private Integer estimatedHours;

    private String notes;

    private String dueDate;

    @NotNull(message = "Project ID is required")
    private Long projectId;

    @NotNull(message = "Assigned user ID is required")
    private Long assignedUserId;
}