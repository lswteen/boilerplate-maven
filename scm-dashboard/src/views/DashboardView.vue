<!-- src/views/DashboardView.vue -->
<template>
  <div class="dashboard">
    <!-- 페이지 헤더 -->
    <div class="page-header">
      <h1 class="page-title">대시보드</h1>
      <p class="page-description">
        SCM 프로젝트 현황을 한눈에 확인하세요
      </p>
    </div>

    <!-- 로딩 상태 -->
    <div v-if="loading" class="loading-section">
      <div class="loading-content">
        <div class="loading-text">Loading...</div>
        <div class="loading-bar">
          <div class="loading-progress"></div>
        </div>
      </div>
    </div>

    <!-- 에러 상태 -->
    <div v-else-if="error" class="error-section">
      <h3 class="error-title">데이터 로드 실패</h3>
      <p class="error-message">{{ error }}</p>
      <button @click="loadDashboardData" class="retry-btn">다시 시도</button>
    </div>

    <!-- 대시보드 컨텐츠 -->
    <div v-else class="dashboard-content">
      <!-- 통계 테이블 -->
      <div class="stats-section">
        <h2 class="section-title">프로젝트 현황</h2>
        <div class="stats-table-container">
          <table class="stats-table">
            <thead>
            <tr>
              <th>구분</th>
              <th>수량</th>
              <th>비율</th>
              <th>상태</th>
            </tr>
            </thead>
            <tbody>
            <tr>
              <td class="category">총 프로젝트</td>
              <td class="value">{{ dashboardData.totalProjects }}</td>
              <td class="percentage">100%</td>
              <td class="status">
                <span class="status-badge total">전체</span>
              </td>
            </tr>
            <tr>
              <td class="category">매핑된 프로젝트</td>
              <td class="value">{{ dashboardData.mappedProjects }}</td>
              <td class="percentage">{{ getMappedPercentage() }}%</td>
              <td class="status">
                <span class="status-badge mapped">매핑</span>
              </td>
            </tr>
            <tr>
              <td class="category">미매핑 프로젝트</td>
              <td class="value">{{ dashboardData.unmappedProjects }}</td>
              <td class="percentage">{{ getUnmappedPercentage() }}%</td>
              <td class="status">
                <span class="status-badge unmapped">미매핑</span>
              </td>
            </tr>
            <tr>
              <td class="category">총 빌드</td>
              <td class="value">{{ dashboardData.totalBuilds }}</td>
              <td class="percentage">-</td>
              <td class="status">
                <span class="status-badge builds">빌드</span>
              </td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>

      <!-- 시각적 그래프 바 -->
      <div class="chart-section">
        <h2 class="section-title">프로젝트 매핑 현황</h2>
        <div class="chart-container">
          <div class="chart-item">
            <div class="chart-label">매핑된 프로젝트</div>
            <div class="chart-bar-container">
              <div
                class="chart-bar mapped"
                :style="{ width: getMappedPercentage() + '%' }"
              ></div>
              <span class="chart-value">{{ dashboardData.mappedProjects }}개 ({{ getMappedPercentage() }}%)</span>
            </div>
          </div>

          <div class="chart-item">
            <div class="chart-label">미매핑 프로젝트</div>
            <div class="chart-bar-container">
              <div
                class="chart-bar unmapped"
                :style="{ width: getUnmappedPercentage() + '%' }"
              ></div>
              <span class="chart-value">{{ dashboardData.unmappedProjects }}개 ({{ getUnmappedPercentage() }}%)</span>
            </div>
          </div>
        </div>
      </div>

      <!-- 상세 통계 테이블 -->
      <div class="details-section">
        <h2 class="section-title">상세 통계</h2>
        <div class="details-grid">
          <div class="detail-table">
            <h3 class="table-title">프로젝트별 빌드 현황</h3>
            <table class="detail-stats-table">
              <thead>
              <tr>
                <th>구분</th>
                <th>프로젝트 수</th>
                <th>빌드 수</th>
                <th>평균 빌드/프로젝트</th>
              </tr>
              </thead>
              <tbody>
              <tr>
                <td>빌드 보유 프로젝트</td>
                <td>{{ dashboardData.projectsWithBuilds }}</td>
                <td>{{ dashboardData.totalBuilds }}</td>
                <td>{{ getAvgBuildsPerProject() }}</td>
              </tr>
              <tr>
                <td>빌드 없는 프로젝트</td>
                <td>{{ dashboardData.projectsWithoutBuilds }}</td>
                <td>0</td>
                <td>0</td>
              </tr>
              </tbody>
            </table>
          </div>

          <div class="detail-table">
            <h3 class="table-title">NULL 프로젝트관리 현황</h3>
            <table class="detail-stats-table">
              <thead>
              <tr>
                <th>구분</th>
                <th>수량</th>
                <th>비고</th>
              </tr>
              </thead>
              <tbody>
              <tr>
                <td>NULL 레코드</td>
                <td>{{ dashboardData.nullProjMgmtCount }}</td>
                <td>처리 필요</td>
              </tr>
              <tr>
                <td>고유 담당자</td>
                <td>{{ dashboardData.uniqueManagers }}</td>
                <td>관리중</td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>

      <!-- 빠른 액션 -->
      <div class="actions-section">
        <h2 class="section-title">빠른 이동</h2>
        <div class="action-buttons">
          <button @click="router.push('/project-builds')" class="action-btn primary">
            빌드 관리
          </button>
          <button @click="router.push('/projects')" class="action-btn secondary">
            프로젝트 관리
          </button>
          <button @click="router.push('/unmapped-projects')" class="action-btn tertiary">
            미매핑 프로젝트
          </button>
          <button @click="router.push('/null-projmgmt')" class="action-btn quaternary">
            NULL 관리
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ProjectService } from '@/services/projectService'

const router = useRouter()

// 반응형 데이터
const loading = ref(false)
const error = ref<string | null>(null)

// 대시보드 데이터
const dashboardData = ref({
  totalProjects: 0,
  mappedProjects: 0,
  unmappedProjects: 0,
  totalBuilds: 0,
  projectsWithBuilds: 0,
  projectsWithoutBuilds: 0,
  nullProjMgmtCount: 0,
  uniqueManagers: 0
})

// 매핑된 프로젝트 비율 계산
const getMappedPercentage = () => {
  if (dashboardData.value.totalProjects === 0) return 0
  return Math.round((dashboardData.value.mappedProjects / dashboardData.value.totalProjects) * 100)
}

// 미매핑 프로젝트 비율 계산
const getUnmappedPercentage = () => {
  if (dashboardData.value.totalProjects === 0) return 0
  return Math.round((dashboardData.value.unmappedProjects / dashboardData.value.totalProjects) * 100)
}

// 평균 빌드/프로젝트 계산
const getAvgBuildsPerProject = () => {
  if (dashboardData.value.projectsWithBuilds === 0) return '0'
  const avg = dashboardData.value.totalBuilds / dashboardData.value.projectsWithBuilds
  return avg.toFixed(1)
}

// 에러 처리 헬퍼
const handleError = (err: any, operation: string) => {
  console.error(`❌ ${operation} 실패:`, err)
  error.value = `${operation}에 실패했습니다.`
  loading.value = false
}

// 대시보드 데이터 로드
const loadDashboardData = async () => {
  loading.value = true
  error.value = null

  try {
    console.log('🚀 Loading dashboard data...')

    // 모든 API 호출을 병렬로 실행
    const [
      allProjectsWithBuilds,
      mappedProjects,
      unmappedProjects,
      nullProjMgmt
    ] = await Promise.all([
      ProjectService.getAllProjectsWithBuilds(),
      ProjectService.getAllProjectsWithProjMgmt(),
      ProjectService.getUnmappedProjects(),
      ProjectService.getNullProjMgmtDtos()
    ])

    // 빌드 데이터 계산
    const totalBuilds = allProjectsWithBuilds.reduce((sum, project) =>
      sum + project.builds.length, 0)
    const projectsWithBuilds = allProjectsWithBuilds.filter(project =>
      project.builds.length > 0).length
    const projectsWithoutBuilds = allProjectsWithBuilds.length - projectsWithBuilds

    // NULL 프로젝트관리 데이터 계산
    const uniqueManagers = new Set(nullProjMgmt.map(item => item.bizMgr).filter(Boolean)).size

    // 전체 프로젝트 수 계산 (매핑된 + 미매핑된)
    const totalProjects = mappedProjects.length + unmappedProjects.length

    // 데이터 업데이트
    dashboardData.value = {
      totalProjects,
      mappedProjects: mappedProjects.length,
      unmappedProjects: unmappedProjects.length,
      totalBuilds,
      projectsWithBuilds,
      projectsWithoutBuilds,
      nullProjMgmtCount: nullProjMgmt.length,
      uniqueManagers
    }

    console.log('✅ Dashboard data loaded successfully:', dashboardData.value)
    loading.value = false

  } catch (err) {
    handleError(err, '대시보드 데이터 로드')
  }
}

// 컴포넌트 마운트 시 데이터 로드
onMounted(() => {
  loadDashboardData()
})
</script>

<style scoped>
.dashboard {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

/* 페이지 헤더 */
.page-header {
  margin-bottom: 32px;
}

.page-title {
  font-size: 32px;
  font-weight: 700;
  color: #2c3e50;
  margin: 0 0 8px 0;
}

.page-description {
  font-size: 16px;
  color: #7f8c8d;
  margin: 0;
}

/* 로딩 및 에러 상태 */
.loading-section, .error-section {
  background: white;
  padding: 60px 40px;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  text-align: center;
  margin-bottom: 24px;
}

.loading-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
}

.loading-text {
  font-size: 18px;
  font-weight: 600;
  color: #2c3e50;
  margin: 0;
}

.loading-bar {
  width: 300px;
  height: 6px;
  background: #ecf0f1;
  border-radius: 3px;
  overflow: hidden;
  position: relative;
}

.loading-progress {
  height: 100%;
  background: linear-gradient(90deg, #3498db, #2980b9, #3498db);
  background-size: 200% 100%;
  border-radius: 3px;
  width: 100%;
  animation: loadingProgress 2s ease-in-out infinite;
}

@keyframes loadingProgress {
  0% {
    transform: translateX(-100%);
    background-position: 200% 0;
  }
  50% {
    background-position: 0% 0;
  }
  100% {
    transform: translateX(100%);
    background-position: -200% 0;
  }
}

.error-title {
  font-size: 20px;
  font-weight: 600;
  color: #2c3e50;
  margin: 0 0 8px 0;
}

.error-message {
  font-size: 16px;
  color: #7f8c8d;
  margin: 0 0 20px 0;
}

.retry-btn {
  padding: 12px 24px;
  background: #3498db;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: background 0.2s ease;
}

.retry-btn:hover {
  background: #2980b9;
}

/* 대시보드 컨텐츠 */
.dashboard-content {
  display: flex;
  flex-direction: column;
  gap: 32px;
}

.section-title {
  font-size: 20px;
  font-weight: 600;
  color: #2c3e50;
  margin: 0 0 16px 0;
}

/* 통계 섹션 */
.stats-section {
  background: white;
  padding: 24px;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.stats-table-container {
  overflow-x: auto;
}

.stats-table {
  width: 100%;
  border-collapse: collapse;
  font-size: 16px;
}

.stats-table th {
  background: #f8f9fa;
  padding: 16px 20px;
  text-align: left;
  font-weight: 600;
  color: #2c3e50;
  border-bottom: 2px solid #e1e8ed;
}

.stats-table td {
  padding: 16px 20px;
  border-bottom: 1px solid #e1e8ed;
  vertical-align: middle;
}

.stats-table tbody tr:hover {
  background: #f8f9fa;
}

.category {
  font-weight: 600;
  color: #2c3e50;
}

.value {
  font-size: 24px;
  font-weight: 700;
  color: #3498db;
}

.percentage {
  font-weight: 600;
  color: #27ae60;
}

.status-badge {
  padding: 6px 12px;
  border-radius: 6px;
  font-size: 12px;
  font-weight: 600;
  text-transform: uppercase;
}

.status-badge.total {
  background: #34495e;
  color: white;
}

.status-badge.mapped {
  background: #27ae60;
  color: white;
}

.status-badge.unmapped {
  background: #e74c3c;
  color: white;
}

.status-badge.builds {
  background: #3498db;
  color: white;
}

/* 차트 섹션 */
.chart-section {
  background: white;
  padding: 24px;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.chart-container {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.chart-item {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.chart-label {
  font-weight: 600;
  color: #2c3e50;
  font-size: 16px;
}

.chart-bar-container {
  position: relative;
  background: #ecf0f1;
  height: 40px;
  border-radius: 20px;
  overflow: hidden;
  display: flex;
  align-items: center;
}

.chart-bar {
  height: 100%;
  border-radius: 20px;
  transition: width 0.8s ease;
  min-width: 80px;
}

.chart-bar.mapped {
  background: linear-gradient(90deg, #27ae60, #2ecc71);
}

.chart-bar.unmapped {
  background: linear-gradient(90deg, #e74c3c, #c0392b);
}

.chart-value {
  position: absolute;
  left: 20px;
  color: white;
  font-weight: 600;
  font-size: 14px;
  z-index: 1;
}

/* 상세 통계 섹션 */
.details-section {
  background: white;
  padding: 24px;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.details-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(400px, 1fr));
  gap: 24px;
}

.detail-table {
  border: 1px solid #e1e8ed;
  border-radius: 8px;
  overflow: hidden;
}

.table-title {
  background: #f8f9fa;
  padding: 16px 20px;
  margin: 0;
  font-size: 16px;
  font-weight: 600;
  color: #2c3e50;
  border-bottom: 1px solid #e1e8ed;
}

.detail-stats-table {
  width: 100%;
  border-collapse: collapse;
  font-size: 14px;
}

.detail-stats-table th {
  background: #f8f9fa;
  padding: 12px 16px;
  text-align: left;
  font-weight: 600;
  color: #2c3e50;
  border-bottom: 1px solid #e1e8ed;
}

.detail-stats-table td {
  padding: 12px 16px;
  border-bottom: 1px solid #e1e8ed;
}

.detail-stats-table tbody tr:hover {
  background: #f8f9fa;
}

/* 액션 섹션 */
.actions-section {
  background: white;
  padding: 24px;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.action-buttons {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 16px;
}

.action-btn {
  padding: 16px 24px;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s ease;
  text-align: center;
}

.action-btn.primary {
  background: #3498db;
  color: white;
}

.action-btn.primary:hover {
  background: #2980b9;
  transform: translateY(-2px);
}

.action-btn.secondary {
  background: #27ae60;
  color: white;
}

.action-btn.secondary:hover {
  background: #219a52;
  transform: translateY(-2px);
}

.action-btn.tertiary {
  background: #9b59b6;
  color: white;
}

.action-btn.tertiary:hover {
  background: #8e44ad;
  transform: translateY(-2px);
}

.action-btn.quaternary {
  background: #e74c3c;
  color: white;
}

.action-btn.quaternary:hover {
  background: #c0392b;
  transform: translateY(-2px);
}

/* 반응형 디자인 */
@media (max-width: 768px) {
  .dashboard {
    padding: 12px;
  }

  .stats-table th,
  .stats-table td {
    padding: 12px 16px;
  }

  .value {
    font-size: 20px;
  }

  .details-grid {
    grid-template-columns: 1fr;
  }

  .action-buttons {
    grid-template-columns: 1fr;
  }

  .loading-bar {
    width: 250px;
  }
}

@media (max-width: 480px) {
  .page-title {
    font-size: 24px;
  }

  .stats-table th,
  .stats-table td {
    padding: 8px 12px;
  }

  .chart-value {
    font-size: 12px;
  }

  .loading-bar {
    width: 200px;
  }
}
</style>
