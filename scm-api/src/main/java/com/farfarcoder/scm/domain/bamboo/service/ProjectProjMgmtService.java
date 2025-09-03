package com.farfarcoder.scm.domain.bamboo.service;

import com.farfarcoder.scm.domain.bamboo.mapper.ProjectProjMgmtMapper;
import com.farfarcoder.scm.domain.bamboo.mapper.SimpleProjectMapper;
import com.farfarcoder.scm.domain.bamboo.model.ProjectProjMgmt;
import com.farfarcoder.scm.domain.bamboo.model.SimpleProject;
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
    private final SimpleProjectMapper simpleProjectMapper;

    /**
     * Project와 ProjMgmt가 매핑된 모든 데이터 조회
     */
    public List<ProjectProjMgmt> findAllProjectsWithProjMgmt() {
        return projectProjMgmtMapper.toModelList(projectProjMgmtRepository.findAllProjectsWithProjMgmt());
    }

    /**
     * ProjMgmt와 매핑되지 않은 모든 Project 조회
     * Project 테이블 총 104개 중 매핑된 82개를 제외한 나머지 조회
     */
    public List<SimpleProject> findProjectsNotMappedToProjMgmt() {
        log.info("Finding projects not mapped to projmgmt");
        List<SimpleProject> result = simpleProjectMapper
                .toModelList(projectProjMgmtRepository.findProjectsNotMappedToProjMgmt());
        log.info("Found {} projects not mapped to projmgmt", result.size());
        return result;
    }

    /**
     * ProjMgmt와 매핑되지 않은 Project 개수 조회
     */
    public Long countProjectsNotMappedToProjMgmt() {
        log.info("Counting projects not mapped to projmgmt");

        Long count = projectProjMgmtRepository.countProjectsNotMappedToProjMgmt();

        log.info("Count of projects not mapped to projmgmt: {}", count);
        return count;
    }

    /**
     * 특정 projectKey로 Project와 ProjMgmt 매핑 데이터 조회
     */
    public Optional<ProjectProjMgmt> findProjectWithProjMgmtByProjectKey(String projectKey) {
        return projectProjMgmtRepository.findProjectWithProjMgmtByProjectKey(projectKey)
                .map(projectProjMgmtMapper::toModel);
    }

    /**
     * 특정 bambooKey로 Project와 ProjMgmt 매핑 데이터 조회
     */
    public Optional<ProjectProjMgmt> findProjectWithProjMgmtByBambooKey(String bambooKey) {
        return projectProjMgmtRepository.findProjectWithProjMgmtByBambooKey(bambooKey)
                .map(projectProjMgmtMapper::toModel);
    }

}
