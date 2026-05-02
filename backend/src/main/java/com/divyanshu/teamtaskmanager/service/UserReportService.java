package com.divyanshu.teamtaskmanager.service;

import com.divyanshu.teamtaskmanager.dto.user.MemberReportResponse;
import com.divyanshu.teamtaskmanager.entity.*;
import com.divyanshu.teamtaskmanager.repository.ProjectMemberRepository;
import com.divyanshu.teamtaskmanager.repository.TaskRepository;
import com.divyanshu.teamtaskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserReportService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final ProjectMemberRepository projectMemberRepository;

    public MemberReportResponse getReport(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        List<Task> tasks =
                taskRepository.findByAssignedTo(user);

        long assigned =
                tasks.size();

        long completed =
                tasks.stream()
                        .filter(t ->
                                t.getStatus() == TaskStatus.COMPLETED
                        )
                        .count();

        long pending =
                tasks.stream()
                        .filter(t ->
                                t.getStatus() == TaskStatus.PENDING
                        )
                        .count();

        long inProgress =
                tasks.stream()
                        .filter(t ->
                                t.getStatus() == TaskStatus.IN_PROGRESS
                        )
                        .count();

        long overdue =
                tasks.stream()
                        .filter(Task::getOverdue)
                        .count();

        long projects =
                projectMemberRepository.findByUser(user).size();

        long completionRate =
                assigned == 0
                        ? 0
                        : (completed * 100) / assigned;

        long efficiency =
                Math.min(
                        100,
                        completionRate
                                + (assigned > 5 ? 10 : 0)
                                - (overdue * 5)
                );

        String badge;

        if (efficiency >= 85) {
            badge = "Top Performer";
        } else if (efficiency >= 60) {
            badge = "Consistent Worker";
        } else {
            badge = "Needs Improvement";
        }

        return MemberReportResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .totalProjects(projects)
                .assignedTasks(assigned)
                .completedTasks(completed)
                .pendingTasks(pending)
                .inProgressTasks(inProgress)
                .overdueTasks(overdue)
                .completionRate(completionRate)
                .efficiencyScore(efficiency)
                .badge(badge)
                .build();
    }
}