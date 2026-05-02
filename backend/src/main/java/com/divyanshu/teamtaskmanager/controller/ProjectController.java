package com.divyanshu.teamtaskmanager.controller;

import com.divyanshu.teamtaskmanager.dto.project.AddMemberRequest;
import com.divyanshu.teamtaskmanager.dto.project.ProjectRequest;
import com.divyanshu.teamtaskmanager.dto.project.ProjectResponse;
import com.divyanshu.teamtaskmanager.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    // ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(
            @Valid @RequestBody ProjectRequest request
    ) {
        return ResponseEntity.ok(
                projectService.createProject(request)
        );
    }

    // ADMIN + MEMBER
    @PreAuthorize("hasAnyRole('ADMIN','MEMBER')")
    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getProjects() {
        return ResponseEntity.ok(
                projectService.getAllProjects()
        );
    }

    // ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{projectId}/members")
    public ResponseEntity<String> addMember(
            @PathVariable Long projectId,
            @Valid @RequestBody AddMemberRequest request
    ) {
        return ResponseEntity.ok(
                projectService.addMember(projectId, request)
        );
    }

    // ADMIN + MEMBER
    @PreAuthorize("hasAnyRole('ADMIN','MEMBER')")
    @GetMapping("/{projectId}/members")
    public ResponseEntity<List<String>> getMembers(
            @PathVariable Long projectId
    ) {
        return ResponseEntity.ok(
                projectService.getMembers(projectId)
        );
    }
}