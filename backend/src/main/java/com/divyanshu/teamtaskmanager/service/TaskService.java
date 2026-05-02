package com.divyanshu.teamtaskmanager.service;

import com.divyanshu.teamtaskmanager.dto.task.TaskRequest;
import com.divyanshu.teamtaskmanager.dto.task.TaskResponse;
import com.divyanshu.teamtaskmanager.entity.Project;
import com.divyanshu.teamtaskmanager.entity.Role;
import com.divyanshu.teamtaskmanager.entity.Task;
import com.divyanshu.teamtaskmanager.entity.TaskStatus;
import com.divyanshu.teamtaskmanager.entity.User;
import com.divyanshu.teamtaskmanager.repository.ProjectRepository;
import com.divyanshu.teamtaskmanager.repository.TaskRepository;
import com.divyanshu.teamtaskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TaskResponse createTask(TaskRequest request) {

        Project project = projectRepository.findById(
                request.getProjectId()
        ).orElseThrow(() ->
                new RuntimeException("Project not found"));

        User assignedUser = userRepository.findById(
                request.getAssignedUserId()
        ).orElseThrow(() ->
                new RuntimeException("User not found"));

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .priority(request.getPriority())
                .category(request.getCategory())
                .estimatedHours(request.getEstimatedHours())
                .notes(request.getNotes())
                .dueDate(
                        request.getDueDate() != null
                                ? LocalDate.parse(request.getDueDate())
                                : null
                )
                .assignedTo(assignedUser)
                .project(project)
                .build();

        taskRepository.save(task);

        return mapTask(task);
    }

    public List<TaskResponse> getAllTasks() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // ADMIN sees all tasks
        if (currentUser.getRole() == Role.ADMIN) {
            return taskRepository.findAll()
                    .stream()
                    .map(this::mapTask)
                    .toList();
        }

        // MEMBER sees own assigned tasks only
        return taskRepository.findByAssignedTo(currentUser)
                .stream()
                .map(this::mapTask)
                .toList();
    }

    public List<TaskResponse> getUserTasks(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return taskRepository.findByAssignedTo(user)
                .stream()
                .map(this::mapTask)
                .toList();
    }

    public String updateStatus(
            Long taskId,
            String status
    ) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new RuntimeException("Task not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // MEMBER can update only own task
        if (currentUser.getRole() == Role.MEMBER &&
                !task.getAssignedTo().getId().equals(currentUser.getId())) {
            throw new RuntimeException(
                    "You can update only your own task"
            );
        }

        TaskStatus newStatus =
                TaskStatus.valueOf(status.toUpperCase());

        task.setStatus(newStatus);

        // progress auto-update
        switch (newStatus) {
            case PENDING -> task.setProgress(0);
            case IN_PROGRESS -> task.setProgress(50);
            case COMPLETED -> task.setProgress(100);
        }

        taskRepository.save(task);

        return "Task status updated successfully";
    }

    private TaskResponse mapTask(Task task) {

        boolean overdue =
                task.getDueDate() != null
                        && task.getDueDate().isBefore(LocalDate.now())
                        && task.getStatus() != TaskStatus.COMPLETED;

        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .priority(task.getPriority())
                .status(task.getStatus())
                .category(task.getCategory())
                .estimatedHours(task.getEstimatedHours())
                .notes(task.getNotes())
                .dueDate(
                        task.getDueDate() != null
                                ? task.getDueDate().toString()
                                : null
                )
                .assignedTo(task.getAssignedTo().getName())
                .projectName(task.getProject().getName())
                .progress(task.getProgress())
                .overdue(overdue)
                .build();
    }
}