package com.divyanshu.teamtaskmanager.repository;

import com.divyanshu.teamtaskmanager.entity.Project;
import com.divyanshu.teamtaskmanager.entity.ProjectMember;
import com.divyanshu.teamtaskmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectMemberRepository
        extends JpaRepository<ProjectMember, Long> {

    List<ProjectMember> findByProject(Project project);

    List<ProjectMember> findByUser(User user);

    Optional<ProjectMember> findByProjectAndUser(
            Project project,
            User user
    );

    boolean existsByProjectAndUser(
            Project project,
            User user
    );

    long countByProject(Project project);
}