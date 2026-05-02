package com.divyanshu.teamtaskmanager.service;

import com.divyanshu.teamtaskmanager.dto.project.AddMemberRequest;
import com.divyanshu.teamtaskmanager.dto.project.ProjectRequest;
import com.divyanshu.teamtaskmanager.dto.project.ProjectResponse;
import com.divyanshu.teamtaskmanager.entity.*;
import com.divyanshu.teamtaskmanager.repository.ProjectMemberRepository;
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
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final TaskRepository taskRepository;

    public ProjectResponse createProject(ProjectRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User admin = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Admin not found"));

        Project project = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .startDate(
                        request.getStartDate() != null &&
                                !request.getStartDate().isBlank()
                                ? LocalDate.parse(request.getStartDate())
                                : null
                )
                .deadline(
                        request.getDeadline() != null &&
                                !request.getDeadline().isBlank()
                                ? LocalDate.parse(request.getDeadline())
                                : null
                )
                .priority(
                        request.getPriority() != null
                                ? request.getPriority()
                                : ProjectPriority.MEDIUM
                )
                .expectedTeamSize(request.getExpectedTeamSize())
                .budget(request.getBudget())
                .riskLevel(
                        request.getRiskLevel() != null
                                ? request.getRiskLevel()
                                : ProjectRiskLevel.LOW
                )
                .notes(request.getNotes())
                .status(ProjectStatus.PLANNING)
                .progress(0)
                .createdBy(admin)
                .build();

        projectRepository.save(project);

        // creator becomes member automatically
        ProjectMember member = ProjectMember.builder()
                .project(project)
                .user(admin)
                .build();

        projectMemberRepository.save(member);

        return mapProject(project);
    }

    public List<ProjectResponse> getAllProjects() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // ADMIN -> all
        if (user.getRole() == Role.ADMIN) {
            return projectRepository.findAll()
                    .stream()
                    .map(this::mapProject)
                    .toList();
        }

        // MEMBER -> joined only
        return projectMemberRepository.findByUser(user)
                .stream()
                .map(ProjectMember::getProject)
                .distinct()
                .map(this::mapProject)
                .toList();
    }

    public String addMember(
            Long projectId,
            AddMemberRequest request
    ) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new RuntimeException("Project not found"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (projectMemberRepository.existsByProjectAndUser(project, user)) {
            return "User already member";
        }

        ProjectMember member = ProjectMember.builder()
                .project(project)
                .user(user)
                .build();

        projectMemberRepository.save(member);

        return "Member added successfully";
    }

    public List<String> getMembers(Long projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new RuntimeException("Project not found"));

        return projectMemberRepository.findByProject(project)
                .stream()
                .map(pm -> pm.getUser().getName())
                .toList();
    }

    private ProjectResponse mapProject(Project project) {

        long members =
                projectMemberRepository.countByProject(project);

        long tasks =
                taskRepository.findByProject(project).size();

        int progress = tasks == 0
                ? 0
                : (int) (
                taskRepository.findByProject(project)
                        .stream()
                        .filter(t ->
                                t.getStatus() == TaskStatus.COMPLETED
                        )
                        .count() * 100 / tasks
        );

        project.setProgress(progress);

        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .startDate(
                        project.getStartDate() != null
                                ? project.getStartDate().toString()
                                : null
                )
                .deadline(
                        project.getDeadline() != null
                                ? project.getDeadline().toString()
                                : null
                )
                .priority(project.getPriority())
                .riskLevel(project.getRiskLevel())
                .status(project.getStatus())
                .expectedTeamSize(project.getExpectedTeamSize())
                .budget(project.getBudget())
                .notes(project.getNotes())
                .progress(progress)
                .totalMembers(members)
                .totalTasks(tasks)
                .build();
    }
}