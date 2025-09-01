package com.farfarcoder.scm.domain.bamboo.repository;

import com.farfarcoder.scm.domain.bamboo.entity.ProjMgmtEntity;
import com.farfarcoder.scm.domain.bamboo.entity.ProjectEntity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@DataJpaTest
@ActiveProfiles("test")
class ProjectProjMgmtRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ProjectProjMgmtRepository projectProjMgmtRepository;

    private ProjectEntity createProject(String projectKey, String title) {
        return ProjectEntity.builder()
                .projectKey(projectKey)
                .title(title)
                .description("Description for " + title)
                .build();
    }

    private ProjMgmtEntity createProjMgmt(String bambooKey, String bizDiv, String bizMgr) {
        return ProjMgmtEntity.builder()
                .bambooKey(bambooKey)
                .bizDiv(bizDiv)
                .bizMgr(bizMgr)
                .config("CONFIG")
                .dev("DEV")
                .oper("OPER")
                .status("ACTIVE")
                .bitbucketName("Bitbucket " + bambooKey)
                .bitbucketKey(bambooKey)
                .bitbucketDesc("Bitbucket Description")
                .bambooName("Bamboo " + bambooKey)
                .bambooDesc("Bamboo Description")
                .deployMgr("Deploy Manager")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }

    @Test
    @DisplayName("매핑된 모든 Project-ProjMgmt 조회")
    void findAllProjectsWithProjMgmt() {
        // Given
        ProjectEntity project1 = createProject("TEST1", "Test Project 1");
        ProjMgmtEntity projMgmt1 = createProjMgmt("TEST1", "DEV", "홍길동");

        ProjectEntity project2 = createProject("TEST2", "Test Project 2");
        ProjMgmtEntity projMgmt2 = createProjMgmt("TEST2", "QA", "김영희");

        ProjectEntity project3 = createProject("TEST3", "Test Project 3"); // ProjMgmt 없음

        testEntityManager.persistAndFlush(project1);

        testEntityManager.persistAndFlush(projMgmt1);
        testEntityManager.persistAndFlush(project2);
        testEntityManager.persistAndFlush(projMgmt2);
        testEntityManager.persistAndFlush(project3);
        testEntityManager.clear();

        // When
        List<ProjectEntity> result = projectProjMgmtRepository.findAllProjectsWithProjMgmt();

        // Then
        assertThat(result).hasSize(2);
        assertThat(result)
                .extracting(ProjectEntity::getProjectKey)
                .containsExactlyInAnyOrder("TEST1", "TEST2");

        result.forEach(project -> {
            assertThat(project.getProjMgmt()).isNotNull();
        });

    }


    @Test
    @DisplayName("ProjectKey로 매핑된 Project-ProjMgmt 조회 - 성공")
    void findProjectWithProjMgmtByProjectKey_Success() {
        // Given
        ProjectEntity project = createProject("DEMO", "Demo Project");
        ProjMgmtEntity projMgmt = createProjMgmt("DEMO", "DEV", "개발자");

        testEntityManager.persistAndFlush(project);
        testEntityManager.persistAndFlush(projMgmt);
        testEntityManager.clear();

        // When
        Optional<ProjectEntity> result = projectProjMgmtRepository
                .findProjectWithProjMgmtByProjectKey("DEMO");

        // Then
        assertThat(result).isPresent();
        ProjectEntity foundProject = result.get();
        assertThat(foundProject.getProjectKey()).isEqualTo("DEMO");
        assertThat(foundProject.getProjMgmt()).isNotNull();
        assertThat(foundProject.getProjMgmt().getBambooKey()).isEqualTo("DEMO");
    }

    @Test
    @DisplayName("ProjectKey로 매핑된 Project-ProjMgmt 조회 - 매핑 없음")
    void findProjectWithProjMgmtByProjectKey_NoMapping() {
        // Given
        ProjectEntity project = createProject("NOMAPPING", "No Mapping Project");
        testEntityManager.persistAndFlush(project);
        testEntityManager.clear();

        // When
        Optional<ProjectEntity> result = projectProjMgmtRepository
                .findProjectWithProjMgmtByProjectKey("NOMAPPING");

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("BambooKey로 매핑된 Project-ProjMgmt 조회 - 성공")
    void findProjectWithProjMgmtByBambooKey_Success() {
        // Given
        ProjectEntity project = createProject("BAMBOO", "Bamboo Project");
        ProjMgmtEntity projMgmt = createProjMgmt("BAMBOO", "PROD", "운영팀");

        testEntityManager.persistAndFlush(project);
        testEntityManager.persistAndFlush(projMgmt);
        testEntityManager.clear();

        // When
        Optional<ProjectEntity> result = projectProjMgmtRepository
                .findProjectWithProjMgmtByBambooKey("BAMBOO");

        // Then
        assertThat(result).isPresent();
        ProjectEntity foundProject = result.get();
        assertThat(foundProject.getProjectKey()).isEqualTo("BAMBOO");
        assertThat(foundProject.getProjMgmt()).isNotNull();
        assertThat(foundProject.getProjMgmt().getBizDiv()).isEqualTo("PROD");
    }

    @Test
    @DisplayName("존재하지 않는 BambooKey로 조회 - 실패")
    void findProjectWithProjMgmtByBambooKey_NotFound() {
        // When
        Optional<ProjectEntity> result = projectProjMgmtRepository
                .findProjectWithProjMgmtByBambooKey("NOTEXIST");

        // Then
        assertThat(result).isEmpty();
    }

}