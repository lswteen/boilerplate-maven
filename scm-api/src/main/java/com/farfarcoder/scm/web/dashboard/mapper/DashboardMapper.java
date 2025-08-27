package com.farfarcoder.scm.web.dashboard.mapper;

import com.farfarcoder.scm.domain.dashboard.model.Dashboard;
import com.farfarcoder.scm.web.dashboard.controller.dto.DashboardResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface DashboardMapper {
    DashboardResponse toResponse(Dashboard dashboard);
    List<DashboardResponse> toResponseList(List<Dashboard> dashboards);
}
