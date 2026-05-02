package com.divyanshu.teamtaskmanager.dto.auth;

import com.divyanshu.teamtaskmanager.entity.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private Role role;
}