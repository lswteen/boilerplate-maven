package com.farfarcoder.mcp.data;
import com.farfarcoder.scm.web.dashboard.service.ProjectBuildAppService;
import com.farfarcoder.scm.web.dashboard.service.ProjectProjMgmtAppService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class CicdDataService{
    private final ProjectProjMgmtAppService projectProjMgmtService;
    private final ProjectBuildAppService projectBuildService;
    public String searchProject(String projectKey) {
        try {
            var projectOpt = projectProjMgmtService.findProjectWithProjMgmtByProjectKey(projectKey);

            if (projectOpt.isPresent()) {
                var project = projectOpt.get();
                return String.format("""
                    ✅ 프로젝트 '%s' 정보:
                    📁 이름: %s
                    📝 설명: %s  
                    👤 담당자: %s
                    🔧 Bamboo: %s
                    📊 상태: %s
                    """,
                        project.projectKey(),
                        project.title(),
                        project.description(),
                        project.bizMgr(),
                        project.bambooName(),
                        project.status()
                );
            } else {
                return String.format("❌ 프로젝트 '%s'를 찾을 수 없습니다.", projectKey);
            }
        } catch (Exception e) {
            log.error("프로젝트 검색 중 오류 발생: {}", e.getMessage());
            return String.format("🚨 프로젝트 '%s' 검색 중 오류가 발생했습니다.", projectKey);
        }
    }

    public String getBuildStatus(String projectKey) {
        try {
            var buildOpt = projectBuildService.findProjectWithBuildsByProjectKey(projectKey);

            if (buildOpt.isPresent()) {
                var projectBuild = buildOpt.get();
                int buildCount = projectBuild.builds().size();

                return String.format("""
                    🔧 프로젝트 '%s' 빌드 현황:
                    📊 총 빌드 수: %d개
                    📁 프로젝트: %s
                    🏗️ 최근 빌드: %s
                    """,
                        projectKey,
                        buildCount,
                        projectBuild.title(),
                        buildCount > 0 ? projectBuild.builds().get(0).buildTitle() : "빌드 없음"
                );
            } else {
                return String.format("❌ 프로젝트 '%s'의 빌드 정보를 찾을 수 없습니다.", projectKey);
            }
        } catch (Exception e) {
            log.error("빌드 상태 조회 중 오류 발생: {}", e.getMessage());
            return String.format("🚨 프로젝트 '%s' 빌드 조회 중 오류가 발생했습니다.", projectKey);
        }
    }
}
