package com.divyanshu.teamtaskmanager.dto.user;

import com.divyanshu.teamtaskmanager.entity.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberReportResponse {

    private Long id;
    private String name;
    private String email;
    private Role role;

    private Long totalProjects;

    private Long assignedTasks;
    private Long completedTasks;
    private Long pendingTasks;
    private Long inProgressTasks;
    private Long overdueTasks;

    private Long completionRate;
    private Long efficiencyScore;

    private String badge;
}