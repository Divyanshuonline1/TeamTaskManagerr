package com.divyanshu.teamtaskmanager.controller;

import com.divyanshu.teamtaskmanager.dto.user.MemberReportResponse;
import com.divyanshu.teamtaskmanager.service.UserReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserReportController {

    private final UserReportService userReportService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/report")
    public ResponseEntity<MemberReportResponse> getReport(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                userReportService.getReport(id)
        );
    }
}