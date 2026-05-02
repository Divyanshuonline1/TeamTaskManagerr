package com.divyanshu.teamtaskmanager.service;

import com.divyanshu.teamtaskmanager.dto.dashboard.DashboardResponse;
import com.divyanshu.teamtaskmanager.entity.Role;
import com.divyanshu.teamtaskmanager.entity.Task;
import com.divyanshu.teamtaskmanager.entity.TaskStatus;
import com.divyanshu.teamtaskmanager.entity.User;
import com.divyanshu.teamtaskmanager.repository.ProjectMemberRepository;
import com.divyanshu.teamtaskmanager.repository.ProjectRepository;
import com.divyanshu.teamtaskmanager.repository.TaskRepository;
import com.divyanshu.teamtaskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectMemberRepository projectMemberRepository;

    public DashboardResponse getDashboardStats() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // ADMIN sees everything
        if (user.getRole() == Role.ADMIN) {
            return DashboardResponse.builder()
                    .totalProjects(projectRepository.count())
                    .totalTasks(taskRepository.count())
                    .pendingTasks(
                            taskRepository.countByStatus(TaskStatus.PENDING)
                    )
                    .inProgressTasks(
                            taskRepository.countByStatus(TaskStatus.IN_PROGRESS)
                    )
                    .completedTasks(
                            taskRepository.countByStatus(TaskStatus.COMPLETED)
                    )
                    .overdueTasks(
                            taskRepository.countByOverdue(true)
                    )
                    .build();
        }

        // MEMBER sees own data only
        List<Task> myTasks =
                taskRepository.findByAssignedTo(user);

        long pending =
                myTasks.stream()
                        .filter(t -> t.getStatus() == TaskStatus.PENDING)
                        .count();

        long inProgress =
                myTasks.stream()
                        .filter(t -> t.getStatus() == TaskStatus.IN_PROGRESS)
                        .count();

        long completed =
                myTasks.stream()
                        .filter(t -> t.getStatus() == TaskStatus.COMPLETED)
                        .count();

        long overdue =
                myTasks.stream()
                        .filter(Task::getOverdue)
                        .count();

        long myProjects =
                projectMemberRepository.findByUser(user).size();

        return DashboardResponse.builder()
                .totalProjects(myProjects)
                .totalTasks(myTasks.size())
                .pendingTasks(pending)
                .inProgressTasks(inProgress)
                .completedTasks(completed)
                .overdueTasks(overdue)
                .build();
    }
}