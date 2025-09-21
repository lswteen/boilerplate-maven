package com.farfarcoder.mcp.service;

import com.farfarcoder.mcp.data.CicdDataService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CicdToolServiceTest {
    @Mock
    private CicdDataService cicdDataService;

    @InjectMocks
    private CicdToolService cicdToolService;

    @Test
    void searchProject_Tool호출() {
        // Given
        String projectKey = "LCTC";
        String expectedResult = "프로젝트 정보";
        when(cicdDataService.searchProject(projectKey)).thenReturn(expectedResult);

        // When
        String result = cicdToolService.searchProject(projectKey);

        // Then
        assertThat(result).isEqualTo(expectedResult);
        verify(cicdDataService).searchProject(projectKey);
    }

    @Test
    void getBuildStatus_Tool호출() {
        // Given
        String projectKey = "LCTC";
        String expectedResult = "빌드 상태";
        when(cicdDataService.getBuildStatus(projectKey)).thenReturn(expectedResult);

        // When
        String result = cicdToolService.getBuildStatus(projectKey);

        // Then
        assertThat(result).isEqualTo(expectedResult);
        verify(cicdDataService).getBuildStatus(projectKey);
    }

}