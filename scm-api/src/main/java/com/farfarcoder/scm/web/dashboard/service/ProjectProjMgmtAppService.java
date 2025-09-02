package com.farfarcoder.scm.web.dashboard.service;

import com.farfarcoder.scm.domain.bamboo.model.Project;
import com.farfarcoder.scm.domain.bamboo.model.ProjectProjMgmt;
import com.farfarcoder.scm.domain.bamboo.service.ProjectProjMgmtService;
import com.farfarcoder.scm.web.dashboard.controller.dto.ProjectProjMgmtResponse;
import com.farfarcoder.scm.web.dashboard.controller.dto.ProjectResponse;
import com.farfarcoder.scm.web.dashboard.mapper.ProjectProjMgmtWebMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectProjMgmtAppService {

    private final ProjectProjMgmtService projectProjMgmtService;
    private final ProjectProjMgmtWebMapper projectProjMgmtWebMapper;

    /**
     * Project와 ProjMgmt가 매핑된 모든 데이터 조회
     */
    public List<ProjectProjMgmtResponse> findAllProjectsWithProjMgmt() {
        log.info("Request to find all projects with projmgmt mapping");

        List<ProjectProjMgmt> projectProjMgmts = projectProjMgmtService.findAllProjectsWithProjMgmt();
        return projectProjMgmtWebMapper.toResponseList(projectProjMgmts);
    }

    /**
     * ProjMgmt와 매핑되지 않은 모든 Project 조회
     * Project 테이블 총 104개 중 매핑된 82개를 제외한 나머지 조회
     */
    public List<ProjectResponse> findProjectsNotMappedToProjMgmt() {
        log.info("Request to find projects not mapped to projmgmt");
        List<Project> projects = projectProjMgmtService.findProjectsNotMappedToProjMgmt();
        return projectProjMgmtWebMapper.toProjectResponseList(projects);
    }

    /**
     * 특정 projectKey로 Project와 ProjMgmt 매핑 데이터 조회
     */
    public Optional<ProjectProjMgmtResponse> findProjectWithProjMgmtByProjectKey(String projectKey) {
        log.info("Request to find project with projmgmt mapping by projectKey: {}", projectKey);

        return projectProjMgmtService.findProjectWithProjMgmtByProjectKey(projectKey)
                .map(projectProjMgmtWebMapper::toResponse);
    }

    /**
     * 특정 bambooKey로 Project와 ProjMgmt 매핑 데이터 조회
     */
    public Optional<ProjectProjMgmtResponse> findProjectWithProjMgmtByBambooKey(String bambooKey) {
        log.info("Request to find project with projmgmt mapping by bambooKey: {}", bambooKey);

        return projectProjMgmtService.findProjectWithProjMgmtByBambooKey(bambooKey)
                .map(projectProjMgmtWebMapper::toResponse);
    }
}
