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
        return projectProjMgmtMapper.toModelList(projectProjMgmtRepository.findAllProjectsWithProjMgmt());
    }

    /**
     * Project와 ProjMgmt BambooKey 가 'NULL' 고아 레코드 데이터 조회
     */
    public List<ProjectProjMgmt> findAllProjectsWithProjMgmtIsNull(){
        return projectProjMgmtMapper.toModelList(projectProjMgmtRepository.findAllOrphanProjMgmt());
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
