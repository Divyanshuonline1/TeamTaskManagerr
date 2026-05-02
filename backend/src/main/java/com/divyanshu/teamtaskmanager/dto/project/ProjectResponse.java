package com.divyanshu.teamtaskmanager.dto.project;

import com.divyanshu.teamtaskmanager.entity.ProjectPriority;
import com.divyanshu.teamtaskmanager.entity.ProjectRiskLevel;
import com.divyanshu.teamtaskmanager.entity.ProjectStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectResponse {

    private Long id;
    private String name;
    private String description;
    private String startDate;
    private String deadline;

    private ProjectPriority priority;
    private ProjectRiskLevel riskLevel;
    private ProjectStatus status;

    private Integer expectedTeamSize;
    private Double budget;
    private String notes;
    private Integer progress;

    private Long totalMembers;
    private Long totalTasks;
}