package com.divyanshu.teamtaskmanager.repository;

import com.divyanshu.teamtaskmanager.entity.Project;
import com.divyanshu.teamtaskmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByCreatedBy(User user);

    List<Project> findByActiveTrue();

    long countByActiveTrue();
}