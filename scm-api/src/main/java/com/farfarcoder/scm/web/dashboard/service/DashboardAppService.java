package com.farfarcoder.scm.web.dashboard.service;

import com.farfarcoder.scm.domain.dashboard.service.DashboardService;
import com.farfarcoder.scm.web.dashboard.controller.dto.DashboardResponse;
import com.farfarcoder.scm.web.dashboard.mapper.DashboardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardAppService {
    private final DashboardService dashboardService;
    private final DashboardMapper dashboardMapper;

    public List<DashboardResponse> findDashboards(String projectKey) {
        return dashboardMapper.toResponseList(
                dashboardService.findProjectAndProjMgmtBuilds(projectKey)
        );
    }
}
