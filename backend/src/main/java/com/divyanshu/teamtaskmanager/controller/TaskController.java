package com.divyanshu.teamtaskmanager.controller;

import com.divyanshu.teamtaskmanager.dto.task.TaskRequest;
import com.divyanshu.teamtaskmanager.dto.task.TaskResponse;
import com.divyanshu.teamtaskmanager.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    // ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody TaskRequest request
    ) {
        return ResponseEntity.ok(
                taskService.createTask(request)
        );
    }

    // ADMIN + MEMBER
    @PreAuthorize("hasAnyRole('ADMIN','MEMBER')")
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasks() {
        return ResponseEntity.ok(
                taskService.getAllTasks()
        );
    }

    // ADMIN + MEMBER
    @PreAuthorize("hasAnyRole('ADMIN','MEMBER')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskResponse>> getUserTasks(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                taskService.getUserTasks(userId)
        );
    }

    // ADMIN + MEMBER
    @PreAuthorize("hasAnyRole('ADMIN','MEMBER')")
    @PatchMapping("/{taskId}/status")
    public ResponseEntity<String> updateStatus(
            @PathVariable Long taskId,
            @RequestParam String status
    ) {
        return ResponseEntity.ok(
                taskService.updateStatus(taskId, status)
        );
    }
}