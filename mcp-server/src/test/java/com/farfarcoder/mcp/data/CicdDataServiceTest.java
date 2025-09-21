package com.farfarcoder.mcp.data;

import com.farfarcoder.scm.web.dashboard.controller.dto.ProjectProjMgmtResponse;
import com.farfarcoder.scm.web.dashboard.service.ProjectProjMgmtAppService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CicdDataServiceTest {
    @Mock
    private ProjectProjMgmtAppService projectProjMgmtService;

    @InjectMocks
    private CicdDataService cicdDataService;

    @Test
    void searchProject_성공() {
        // Given
        String projectKey = "LCTC";
        ProjectProjMgmtResponse mockResponse = createMockProjectResponse();
        when(projectProjMgmtService.findProjectWithProjMgmtByProjectKey(projectKey))
                .thenReturn(Optional.of(mockResponse));

        // When
        String result = cicdDataService.searchProject(projectKey);

        // Then
        assertThat(result).contains("LCTC");
        assertThat(result).contains("프로젝트");
    }

    @Test
    void searchProject_프로젝트없음() {
        // Given
        String projectKey = "NOTFOUND";
        when(projectProjMgmtService.findProjectWithProjMgmtByProjectKey(projectKey))
                .thenReturn(Optional.empty());

        // When
        String result = cicdDataService.searchProject(projectKey);

        // Then
        assertThat(result).contains("찾을 수 없습니다");
    }

    private ProjectProjMgmtResponse createMockProjectResponse() {
        // Mock 데이터 생성 (실제 record 구조에 맞게)
        return new ProjectProjMgmtResponse(
                1L, "LCTC", "테스트 프로젝트", "설명",
                1L, "업무구분", "담당자", "Y", "Y", "Y",
                "ACTIVE", "BitbucketName", "BB_KEY", "설명",
                "BambooName", "BAMBOO_KEY", "설명", "배포담당자",
                Instant.now(), Instant.now()
        );
    }
}