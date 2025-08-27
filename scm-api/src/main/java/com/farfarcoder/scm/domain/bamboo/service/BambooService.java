package com.farfarcoder.scm.domain.bamboo.service;

import com.farfarcoder.scm.domain.bamboo.entity.BuildEntity;
import com.farfarcoder.scm.domain.bamboo.entity.ProjMgmtEntity;
import com.farfarcoder.scm.domain.bamboo.entity.ProjectEntity;
import com.farfarcoder.scm.domain.bamboo.repository.BuildRepository;
import com.farfarcoder.scm.domain.bamboo.repository.ProjMgmtRepository;
import com.farfarcoder.scm.domain.bamboo.repository.ProjectRepository;
import com.farfarcoder.scm.domain.dashboard.model.Dashboard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BambooService {
    private final ProjectRepository projectRepository;
    private final BuildRepository buildRepository;
    private final ProjMgmtRepository projMgmtRepository;

    private Dashboard createProjectDashboard(ProjectEntity project){
        ProjMgmtEntity projMgmt = project.getProjMgmt(); // 자동 매핑

        return Dashboard.builder()
                .levelType(1)
                .projectId(project.getProjectId())
                .projectKey(project.getProjectKey())
                .title(project.getTitle())
                .description(project.getDescription())
                .buildId(null)
                .buildTitle(null)
                .buildType(null)
                .buildSeq(1L)
                // PROJMGMT 데이터 (자동 매핑)
                .bambooKey(projMgmt != null ? projMgmt.getBambooKey() : null)
                .bizDiv(projMgmt != null ? projMgmt.getBizDiv() : null)
                .bizMgr(projMgmt != null ? projMgmt.getBizMgr() : null)
                .config(projMgmt != null ? projMgmt.getConfig() : null)
                .dev(projMgmt != null ? projMgmt.getDev() : null)
                .oper(projMgmt != null ? projMgmt.getOper() : null)
                .status(projMgmt != null ? projMgmt.getStatus() : null)
                .bitbucketName(projMgmt != null ? projMgmt.getBitbucketName() : null)
                .bitbucketKey(projMgmt != null ? projMgmt.getBitbucketKey() : null)
                .bitbucketDesc(projMgmt != null ? projMgmt.getBitbucketDesc() : null)
                .bambooDesc(projMgmt != null ? projMgmt.getBambooDesc() : null)
                .deployMgr(projMgmt != null ? projMgmt.getDeployMgr() : null)
                .build();
    }

    private Dashboard createBuildDashboard(ProjectEntity project, BuildEntity build, int sequence){
        ProjMgmtEntity projMgmt = project.getProjMgmt(); // 자동 매핑

        return Dashboard.builder()
                .levelType(2)
                .projectId(project.getProjectId())
                .projectKey(project.getProjectKey())
                .title(project.getTitle())
                .description(project.getDescription())
                .buildId(build.getBuildId())
                .buildTitle(build.getBuildTitle())
                .buildType(build.getBuildType())
                .buildSeq((long) sequence)
                // PROJMGMT 데이터 (자동 매핑)
                .bambooKey(projMgmt != null ? projMgmt.getBambooKey() : null)
                .bizDiv(projMgmt != null ? projMgmt.getBizDiv() : null)
                .bizMgr(projMgmt != null ? projMgmt.getBizMgr() : null)
                .config(projMgmt != null ? projMgmt.getConfig() : null)
                .dev(projMgmt != null ? projMgmt.getDev() : null)
                .oper(projMgmt != null ? projMgmt.getOper() : null)
                .status(projMgmt != null ? projMgmt.getStatus() : null)
                .bitbucketName(projMgmt != null ? projMgmt.getBitbucketName() : null)
                .bitbucketKey(projMgmt != null ? projMgmt.getBitbucketKey() : null)
                .bitbucketDesc(projMgmt != null ? projMgmt.getBitbucketDesc() : null)
                .bambooDesc(projMgmt != null ? projMgmt.getBambooDesc() : null)
                .deployMgr(projMgmt != null ? projMgmt.getDeployMgr() : null)
                .build();
    }

    private Dashboard createOrphanProjMgmtDashboard(ProjMgmtEntity projMgmt, long sequence){
        return Dashboard.builder()
                .levelType(3) // 매핑 안되는 데이터 구분용
                .projectId(null)
                .projectKey(projMgmt.getBambooKey())
                .title("[매핑없음] " + (projMgmt.getBizDiv() != null ? projMgmt.getBizDiv() : projMgmt.getBambooKey()))
                .description("프로젝트와 매핑되지 않은 PROJMGMT 데이터")
                .buildId(null)
                .buildTitle(null)
                .buildType(null)
                .buildSeq(sequence)
                // PROJMGMT 데이터
                .bambooKey(projMgmt.getBambooKey())
                .bizDiv(projMgmt.getBizDiv())
                .bizMgr(projMgmt.getBizMgr())
                .config(projMgmt.getConfig())
                .dev(projMgmt.getDev())
                .oper(projMgmt.getOper())
                .status(projMgmt.getStatus())
                .bitbucketName(projMgmt.getBitbucketName())
                .bitbucketKey(projMgmt.getBitbucketKey())
                .bitbucketDesc(projMgmt.getBitbucketDesc())
                .bambooDesc(projMgmt.getBambooDesc())
                .deployMgr(projMgmt.getDeployMgr())
                .build();
    }

    public List<Dashboard> findProjectAndProjMgmtBuilds(String projectKey){
        List<Dashboard> dashboards = new ArrayList<>();
        List<ProjectEntity> projectEntities;

        // PROJMGMT와 함께 조회 (한 번의 쿼리로 JOIN)
        if(!StringUtils.hasText(projectKey)){
            projectEntities = projectRepository.findAllWithProjMgmtOrderByTitleAsc();
        }else{
            projectEntities = projectRepository.findByProjectKeyWithProjMgmtOrderByTitleAsc(projectKey);
        }

        // 프로젝트와 빌드 처리
        for(ProjectEntity projectEntity : projectEntities){
            dashboards.add(createProjectDashboard(projectEntity));

            List<BuildEntity> buildEntities = buildRepository.findByProjectIdAndBuildTypeOrderByBuildIdAsc(projectEntity.getProjectId(),"CHAIN");
            int index = 1;
            for(BuildEntity buildEntity : buildEntities){
                dashboards.add(createBuildDashboard(projectEntity, buildEntity, index++));
            }
        }

        // 매핑되지 않는 PROJMGMT를 맨 아래 추가 (전체 조회일 때만)
        if(!StringUtils.hasText(projectKey)){
            List<ProjMgmtEntity> orphanProjMgmts = projMgmtRepository.findOrphanProjMgmtOrderByBambooKeyDesc();
            long sequence = 1;
            for(ProjMgmtEntity orphanProjMgmt : orphanProjMgmts){
                dashboards.add(createOrphanProjMgmtDashboard(orphanProjMgmt, sequence++));
            }
        }

        return dashboards;
    }

    // 기존 메서드들 - 간소화됨
    public List<ProjMgmtEntity> findAllProjMgmt() {
        return projMgmtRepository.findAllByOrderByBambooKeyDesc();
    }

    public Optional<ProjMgmtEntity> findProjMgmtByBambooKey(String bambooKey) {
        return projMgmtRepository.findByBambooKey(bambooKey);
    }

    public List<ProjMgmtEntity> findOrphanProjMgmt() {
        return projMgmtRepository.findOrphanProjMgmtOrderByBambooKeyDesc();
    }

    public List<ProjectEntity> findProjectsWithProjMgmt() {
        return projectRepository.findProjectsWithProjMgmtOrderByTitleAsc();
    }

    public List<ProjectEntity> findProjectsWithoutProjMgmt() {
        return projectRepository.findProjectsWithoutProjMgmtOrderByTitleAsc();
    }
}
