package com.farfarcoder.scm.domain.bamboo.service;

import com.farfarcoder.scm.domain.bamboo.entity.ProjMgmtEntity;
import com.farfarcoder.scm.domain.bamboo.entity.ProjectEntity;
import com.farfarcoder.scm.domain.bamboo.mapper.ProjectProjMgmtMapper;
import com.farfarcoder.scm.domain.bamboo.model.ProjectProjMgmt;
import com.farfarcoder.scm.domain.bamboo.repository.ProjectProjMgmtRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProjectProjMgmtService 테스트")
class ProjectProjMgmtServiceTest {
    @Mock
    private ProjectProjMgmtRepository projectProjMgmtRepository;

    @Mock
    private ProjectProjMgmtMapper projectProjMgmtMapper;

    @InjectMocks
    private ProjectProjMgmtService projectProjMgmtService;

    // Helper methods for creating mock objects
    private List<ProjectEntity> createMockProjectEntities() {
        ProjectEntity project1 = createMockProjectEntity("PROJECT1", "Project 1");
        ProjectEntity project2 = createMockProjectEntity("PROJECT2", "Project 2");
        return Arrays.asList(project1, project2);
    }

    private ProjectEntity createMockProjectEntity(String projectKey, String title) {
        ProjMgmtEntity projMgmt = ProjMgmtEntity.builder()
                .id(1L)
                .bambooKey(projectKey)
                .bizDiv("DEV")
                .bizMgr("Manager")
                .config("CONFIG")
                .dev("DEV_TEAM")
                .oper("OPS_TEAM")
                .status("ACTIVE")
                .bitbucketName("Bitbucket " + projectKey)
                .bitbucketKey(projectKey)
                .bitbucketDesc("Bitbucket Description")
                .bambooName("Bamboo " + projectKey)
                .bambooDesc("Bamboo Description")
                .deployMgr("Deploy Manager")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        return ProjectEntity.builder()
                .projectId(1L)
                .projectKey(projectKey)
                .title(title)
                .description("Description for " + title)
                .projMgmt(projMgmt)
                .build();
    }

    private List<ProjectProjMgmt> createMockProjectProjMgmts() {
        ProjectProjMgmt project1 = createMockProjectProjMgmt("PROJECT1", "Project 1");
        ProjectProjMgmt project2 = createMockProjectProjMgmt("PROJECT2", "Project 2");
        return Arrays.asList(project1, project2);
    }

    private ProjectProjMgmt createMockProjectProjMgmt(String projectKey, String title) {
        return ProjectProjMgmt.builder()
                .projectId(1L)
                .projectKey(projectKey)
                .title(title)
                .description("Description for " + title)
                .projMgmtId(1L)
                .bizDiv("DEV")
                .bizMgr("Manager")
                .config("CONFIG")
                .dev("DEV_TEAM")
                .oper("OPS_TEAM")
                .status("ACTIVE")
                .bitbucketName("Bitbucket " + projectKey)
                .bitbucketKey(projectKey)
                .bitbucketDesc("Bitbucket Description")
                .bambooName("Bamboo " + projectKey)
                .bambooKey(projectKey)
                .bambooDesc("Bamboo Description")
                .deployMgr("Deploy Manager")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }

    @Test
    @DisplayName("매핑된 모든 프로젝트 조회 - 성공")
    void findAllProjectsWithProjMgmt_Success() {
        // Given
        List<ProjectEntity> mockProjectEntities = createMockProjectEntities();
        List<ProjectProjMgmt> expectedResult = createMockProjectProjMgmts();

        given(projectProjMgmtRepository.findAllProjectsWithProjMgmt()).willReturn(mockProjectEntities);
        given(projectProjMgmtMapper.toModelList(anyList())).willReturn(expectedResult);

        // When
        List<ProjectProjMgmt> result = projectProjMgmtService.findAllProjectsWithProjMgmt();

        // Then
        assertThat(result).hasSize(2);
        assertThat(result).isEqualTo(expectedResult);

        verify(projectProjMgmtRepository).findAllProjectsWithProjMgmt();
        verify(projectProjMgmtMapper).toModelList(mockProjectEntities);
    }

    @Test
    @DisplayName("매핑된 모든 프로젝트 조회 - 빈 결과")
    void findAllProjectsWithProjMgmt_EmptyResult() {
        // Given
        given(projectProjMgmtRepository.findAllProjectsWithProjMgmt()).willReturn(Arrays.asList());
        given(projectProjMgmtMapper.toModelList(anyList())).willReturn(Arrays.asList());

        // When
        List<ProjectProjMgmt> result = projectProjMgmtService.findAllProjectsWithProjMgmt();

        // Then
        assertThat(result).isEmpty();

        verify(projectProjMgmtRepository).findAllProjectsWithProjMgmt();
        verify(projectProjMgmtMapper).toModelList(Arrays.asList());
    }

    @Test
    @DisplayName("프로젝트 키로 매핑 조회 - 성공")
    void findProjectWithProjMgmtByProjectKey_Success() {
        // Given
        String projectKey = "TEST_PROJECT";
        ProjectEntity mockProjectEntity = createMockProjectEntity(projectKey, "Test Project");
        ProjectProjMgmt expectedResult = createMockProjectProjMgmt(projectKey, "Test Project");

        given(projectProjMgmtRepository.findProjectWithProjMgmtByProjectKey(projectKey))
                .willReturn(Optional.of(mockProjectEntity));
        given(projectProjMgmtMapper.toModel(any(ProjectEntity.class)))
                .willReturn(expectedResult);

        // When
        Optional<ProjectProjMgmt> result = projectProjMgmtService.findProjectWithProjMgmtByProjectKey(projectKey);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(expectedResult);
        assertThat(result.get().projectKey()).isEqualTo(projectKey);

        verify(projectProjMgmtRepository).findProjectWithProjMgmtByProjectKey(projectKey);
        verify(projectProjMgmtMapper).toModel(mockProjectEntity);
    }

    @Test
    @DisplayName("프로젝트 키로 매핑 조회 - 결과 없음")
    void findProjectWithProjMgmtByProjectKey_NotFound() {
        // Given
        String projectKey = "NON_EXISTENT_PROJECT";

        given(projectProjMgmtRepository.findProjectWithProjMgmtByProjectKey(projectKey))
                .willReturn(Optional.empty());

        // When
        Optional<ProjectProjMgmt> result = projectProjMgmtService.findProjectWithProjMgmtByProjectKey(projectKey);

        // Then
        assertThat(result).isEmpty();

        verify(projectProjMgmtRepository).findProjectWithProjMgmtByProjectKey(projectKey);
    }

    @Test
    @DisplayName("밤부 키로 매핑 조회 - 성공")
    void findProjectWithProjMgmtByBambooKey_Success() {
        // Given
        String bambooKey = "BAMBOO_KEY";
        ProjectEntity mockProjectEntity = createMockProjectEntity("PROJECT_KEY", "Project Title");
        ProjectProjMgmt expectedResult = createMockProjectProjMgmt("PROJECT_KEY", "Project Title");

        given(projectProjMgmtRepository.findProjectWithProjMgmtByBambooKey(bambooKey))
                .willReturn(Optional.of(mockProjectEntity));
        given(projectProjMgmtMapper.toModel(any(ProjectEntity.class)))
                .willReturn(expectedResult);

        // When
        Optional<ProjectProjMgmt> result = projectProjMgmtService.findProjectWithProjMgmtByBambooKey(bambooKey);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(expectedResult);

        verify(projectProjMgmtRepository).findProjectWithProjMgmtByBambooKey(bambooKey);
        verify(projectProjMgmtMapper).toModel(mockProjectEntity);
    }

    @Test
    @DisplayName("밤부 키로 매핑 조회 - 결과 없음")
    void findProjectWithProjMgmtByBambooKey_NotFound() {
        // Given
        String bambooKey = "NON_EXISTENT_BAMBOO";

        given(projectProjMgmtRepository.findProjectWithProjMgmtByBambooKey(bambooKey))
                .willReturn(Optional.empty());

        // When
        Optional<ProjectProjMgmt> result = projectProjMgmtService.findProjectWithProjMgmtByBambooKey(bambooKey);

        // Then
        assertThat(result).isEmpty();

        verify(projectProjMgmtRepository).findProjectWithProjMgmtByBambooKey(bambooKey);
    }

    @Test
    @DisplayName("null 파라미터로 프로젝트 키 조회")
    void findProjectWithProjMgmtByProjectKey_NullParameter() {
        // Given
        given(projectProjMgmtRepository.findProjectWithProjMgmtByProjectKey(null))
                .willReturn(Optional.empty());

        // When
        Optional<ProjectProjMgmt> result = projectProjMgmtService.findProjectWithProjMgmtByProjectKey(null);

        // Then
        assertThat(result).isEmpty();
        verify(projectProjMgmtRepository).findProjectWithProjMgmtByProjectKey(null);
    }

    @Test
    @DisplayName("null 파라미터로 밤부 키 조회")
    void findProjectWithProjMgmtByBambooKey_NullParameter() {
        // Given
        given(projectProjMgmtRepository.findProjectWithProjMgmtByBambooKey(null))
                .willReturn(Optional.empty());

        // When
        Optional<ProjectProjMgmt> result = projectProjMgmtService.findProjectWithProjMgmtByBambooKey(null);

        // Then
        assertThat(result).isEmpty();
        verify(projectProjMgmtRepository).findProjectWithProjMgmtByBambooKey(null);
    }
}