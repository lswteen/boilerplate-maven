package com.farfarcoder.scm.domain.bamboo.service;

import com.farfarcoder.scm.domain.bamboo.entity.ProjectEntity;
import com.farfarcoder.scm.domain.bamboo.mapper.ProjectProjMgmtMapper;
import com.farfarcoder.scm.domain.bamboo.model.ProjectProjMgmt;
import com.farfarcoder.scm.domain.bamboo.repository.ProjectProjMgmtRepository;
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
public class ProjectProjMgmtService {
    private final ProjectProjMgmtRepository projectProjMgmtRepository;
    private final ProjectProjMgmtMapper projectProjMgmtMapper;

    /**
     * Project와 ProjMgmt가 매핑된 모든 데이터 조회
     */
    public List<ProjectProjMgmt> findAllProjectsWithProjMgmt() {
        log.debug("Finding all projects with projmgmt mapping");

        List<ProjectEntity> projectEntities = projectProjMgmtRepository.findAllProjectsWithProjMgmt();
        return projectProjMgmtMapper.toModelList(projectEntities);
    }

    /**
     * 특정 projectKey로 Project와 ProjMgmt 매핑 데이터 조회
     */
    public Optional<ProjectProjMgmt> findProjectWithProjMgmtByProjectKey(String projectKey) {
        log.debug("Finding project with projmgmt mapping by projectKey: {}", projectKey);

        return projectProjMgmtRepository.findProjectWithProjMgmtByProjectKey(projectKey)
                .map(projectProjMgmtMapper::toModel);
    }

    /**
     * 특정 bambooKey로 Project와 ProjMgmt 매핑 데이터 조회
     */
    public Optional<ProjectProjMgmt> findProjectWithProjMgmtByBambooKey(String bambooKey) {
        log.debug("Finding project with projmgmt mapping by bambooKey: {}", bambooKey);

        return projectProjMgmtRepository.findProjectWithProjMgmtByBambooKey(bambooKey)
                .map(projectProjMgmtMapper::toModel);
    }

}
