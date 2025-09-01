package com.farfarcoder.scm.domain.bamboo.service;

import com.farfarcoder.scm.domain.bamboo.entity.ProjectEntity;
import com.farfarcoder.scm.domain.bamboo.mapper.ProjectBuildMapper;
import com.farfarcoder.scm.domain.bamboo.model.ProjectBuild;
import com.farfarcoder.scm.domain.bamboo.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectBuildService {
    private final ProjectRepository projectRepository;
    private final ProjectBuildMapper projectBuildMapper;

    /**
     * 모든 Project와 Build 데이터 조회
     */
    public List<ProjectBuild> findAllProjectsWithBuilds() {
        log.debug("Finding all projects with builds");

        List<ProjectEntity> projectEntities = projectRepository.findAllWithBuildsOrderByTitleAsc();
        return projectBuildMapper.toModelList(projectEntities);
    }

    /**
     * 특정 projectKey로 Project와 Build 데이터 조회
     */
    public Optional<ProjectBuild> findProjectWithBuildsByProjectKey(String projectKey) {
        log.debug("Finding project with builds by projectKey: {}", projectKey);

        return projectRepository.findByProjectKeyWithBuildsOrderByTitleAsc(projectKey)
                .map(projectBuildMapper::toModel);
    }

    /**
     * 특정 projectId로 Project와 Build 데이터 조회
     */
    public Optional<ProjectBuild> findProjectWithBuildsByProjectId(Long projectId) {
        log.debug("Finding project with builds by projectId: {}", projectId);

        return projectRepository.findByIdWithBuilds(projectId)
                .map(projectBuildMapper::toModel);
    }
}
