package com.farfarcoder.scm.web.dashboard.service;

import com.farfarcoder.scm.domain.bamboo.model.ProjectBuild;
import com.farfarcoder.scm.domain.bamboo.service.ProjectBuildService;
import com.farfarcoder.scm.web.dashboard.controller.dto.ProjectBuildResponse;
import com.farfarcoder.scm.web.dashboard.mapper.ProjectBuildWebMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectBuildAppService {
    private final ProjectBuildService projectBuildService;
    private final ProjectBuildWebMapper projectBuildWebMapper;

    /**
     * 모든 Project와 Build 데이터 조회
     */
    public List<ProjectBuildResponse> findAllProjectsWithBuilds() {
        log.info("Request to find all projects with builds");

        List<ProjectBuild> projectBuilds = projectBuildService.findAllProjectsWithBuilds();
        return projectBuildWebMapper.toResponseList(projectBuilds);
    }

    /**
     * 특정 projectKey로 Project와 Build 데이터 조회
     */
    public Optional<ProjectBuildResponse> findProjectWithBuildsByProjectKey(String projectKey) {
        log.info("Request to find project with builds by projectKey: {}", projectKey);

        return projectBuildService.findProjectWithBuildsByProjectKey(projectKey)
                .map(projectBuildWebMapper::toResponse);
    }

    /**
     * 특정 projectId로 Project와 Build 데이터 조회
     */
    public Optional<ProjectBuildResponse> findProjectWithBuildsByProjectId(Long projectId) {
        log.info("Request to find project with builds by projectId: {}", projectId);

        return projectBuildService.findProjectWithBuildsByProjectId(projectId)
                .map(projectBuildWebMapper::toResponse);
    }
}
