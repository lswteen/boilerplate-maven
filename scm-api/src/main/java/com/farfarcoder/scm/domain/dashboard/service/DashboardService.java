package com.farfarcoder.scm.domain.dashboard.service;

import com.farfarcoder.scm.domain.bamboo.service.BambooService;
import com.farfarcoder.scm.domain.dashboard.model.Dashboard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardService {

    private final BambooService bambooService;

    public List<Dashboard> findProjectAndProjMgmtBuilds(String projectKey){
        return bambooService.findProjectAndProjMgmtBuilds(projectKey);
    }

}
