package com.divyanshu.teamtaskmanager.dto.project;

import com.divyanshu.teamtaskmanager.entity.ProjectPriority;
import com.divyanshu.teamtaskmanager.entity.ProjectRiskLevel;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProjectRequest {

    @NotBlank
    private String name;

    private String description;

    private String startDate;

    private String deadline;

    private ProjectPriority priority;

    private Integer expectedTeamSize;

    private Double budget;

    private ProjectRiskLevel riskLevel;

    private String notes;
}