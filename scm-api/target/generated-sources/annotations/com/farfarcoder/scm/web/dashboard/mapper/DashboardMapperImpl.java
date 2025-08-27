package com.farfarcoder.scm.web.dashboard.mapper;

import com.farfarcoder.scm.domain.dashboard.model.Dashboard;
import com.farfarcoder.scm.web.dashboard.controller.dto.DashboardResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-27T18:36:33+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class DashboardMapperImpl implements DashboardMapper {

    @Override
    public DashboardResponse toResponse(Dashboard dashboard) {
        if ( dashboard == null ) {
            return null;
        }

        DashboardResponse.DashboardResponseBuilder dashboardResponse = DashboardResponse.builder();

        dashboardResponse.levelType( dashboard.levelType() );
        dashboardResponse.projectId( dashboard.projectId() );
        dashboardResponse.projectKey( dashboard.projectKey() );
        dashboardResponse.title( dashboard.title() );
        dashboardResponse.description( dashboard.description() );
        dashboardResponse.buildId( dashboard.buildId() );
        dashboardResponse.buildTitle( dashboard.buildTitle() );
        dashboardResponse.buildType( dashboard.buildType() );
        dashboardResponse.buildSeq( dashboard.buildSeq() );
        dashboardResponse.bambooKey( dashboard.bambooKey() );
        dashboardResponse.bizDiv( dashboard.bizDiv() );
        dashboardResponse.bizMgr( dashboard.bizMgr() );
        dashboardResponse.config( dashboard.config() );
        dashboardResponse.dev( dashboard.dev() );
        dashboardResponse.oper( dashboard.oper() );
        dashboardResponse.status( dashboard.status() );
        dashboardResponse.bitbucketName( dashboard.bitbucketName() );
        dashboardResponse.bitbucketKey( dashboard.bitbucketKey() );
        dashboardResponse.bitbucketDesc( dashboard.bitbucketDesc() );
        dashboardResponse.bambooDesc( dashboard.bambooDesc() );
        dashboardResponse.deployMgr( dashboard.deployMgr() );

        return dashboardResponse.build();
    }

    @Override
    public List<DashboardResponse> toResponseList(List<Dashboard> dashboards) {
        if ( dashboards == null ) {
            return null;
        }

        List<DashboardResponse> list = new ArrayList<DashboardResponse>( dashboards.size() );
        for ( Dashboard dashboard : dashboards ) {
            list.add( toResponse( dashboard ) );
        }

        return list;
    }
}
