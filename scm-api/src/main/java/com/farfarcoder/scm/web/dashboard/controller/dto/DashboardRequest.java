package com.farfarcoder.scm.web.dashboard.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DashboardRequest {
    private String projectKey;
}
