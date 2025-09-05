<!-- src/views/ProjectBuildsView.vue -->
<template>
  <div class="project-builds">
    <!-- 페이지 헤더 -->
    <div class="page-header">
      <h1 class="page-title">빌드 관리</h1>
      <p class="page-description">
        프로젝트별 빌드 정보를 조회하고 관리하세요
      </p>
    </div>

    <!-- 검색 및 필터 영역 -->
    <div class="search-section">
      <div class="search-container">
        <div class="search-group">
          <label class="search-label">프로젝트 키 검색</label>
          <div class="search-input-group">
            <input
              v-model="searchProjectKey"
              type="text"
              placeholder="프로젝트 키를 입력하세요 (예: LCTC)"
              class="search-input"
              @keyup.enter="handleSearch"
            />
            <button
              @click="handleSearch"
              class="search-btn"
              :disabled="loading"
            >
              검색
            </button>
            <button
              @click="handleReset"
              class="reset-btn"
              :disabled="loading"
            >
              초기화
            </button>
          </div>
        </div>

        <!-- ID 검색 그룹 -->
        <div class="search-group">
          <label class="search-label">프로젝트 ID 검색</label>
          <div class="search-input-group">
            <input
              v-model="searchProjectId"
              type="number"
              placeholder="프로젝트 ID를 입력하세요"
              class="search-input"
              @keyup.enter="handleSearchById"
            />
            <button
              @click="handleSearchById"
              class="search-btn"
              :disabled="loading || !searchProjectId"
            >
              ID 검색
            </button>
          </div>
        </div>

        <!-- 빠른 액션 버튼들 -->
        <div class="action-buttons">
          <button
            @click="loadAllProjects"
            class="action-btn primary"
            :disabled="loading"
          >
            전체 조회
          </button>
          <button
            @click="downloadExcel"
            class="action-btn secondary"
            :disabled="loading || projectBuilds.length === 0"
          >
            Excel 다운로드
          </button>
        </div>
      </div>
    </div>

    <!-- 통계 카드 -->
    <div class="stats-section" v-if="stats">
      <div class="stat-card">
        <div class="stat-content">
          <div class="stat-value">{{ stats.totalProjects }}</div>
          <div class="stat-label">총 프로젝트</div>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-content">
          <div class="stat-value">{{ stats.totalBuilds }}</div>
          <div class="stat-label">총 빌드</div>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-content">
          <div class="stat-value">{{ stats.avgBuildsPerProject }}</div>
          <div class="stat-label">평균 빌드/프로젝트</div>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-content">
          <div class="stat-value">{{ stats.projectsWithoutBuilds }}</div>
          <div class="stat-label">빌드 없는 프로젝트</div>
        </div>
      </div>
    </div>

    <!-- 로딩 상태 -->
    <div v-if="loading" class="loading-section">
      <p class="loading-text">데이터를 불러오는 중입니다...</p>
    </div>

    <!-- 에러 상태 -->
    <div v-else-if="error" class="error-section">
      <p class="error-message">{{ error }}</p>
      <button @click="retryLoad" class="retry-btn">다시 시도</button>
    </div>

    <!-- 데이터 없음 상태 -->
    <div v-else-if="projectBuilds.length === 0 && !loading" class="empty-section">
      <h3 class="empty-title">조회 결과가 없습니다</h3>
      <p class="empty-message">
        {{ searchProjectKey || searchProjectId ?
        `검색 조건에 해당하는 프로젝트를 찾을 수 없습니다.` :
        '등록된 프로젝트-빌드 데이터가 없습니다.'
        }}
      </p>
    </div>

    <!-- 프로젝트-빌드 목록 -->
    <div v-else class="content-section">
      <div class="results-header">
        <h2 class="results-title">
          조회 결과 ({{ projectBuilds.length }}개 프로젝트)
        </h2>
        <div class="view-options">
          <button
            @click="viewMode = 'card'"
            class="view-btn"
            :class="{ active: viewMode === 'card' }"
          >
            카드뷰
          </button>
          <button
            @click="viewMode = 'table'"
            class="view-btn"
            :class="{ active: viewMode === 'table' }"
          >
            테이블뷰
          </button>
          <div v-if="viewMode === 'card'" class="expand-controls">
            <button
              @click="toggleAllProjects(true)"
              class="expand-btn"
              :disabled="projectBuilds.length === 0"
            >
              모두 펼치기
            </button>
            <button
              @click="toggleAllProjects(false)"
              class="expand-btn"
              :disabled="projectBuilds.length === 0"
            >
              모두 접기
            </button>
          </div>
        </div>
      </div>

      <!-- 카드 뷰 -->
      <div v-if="viewMode === 'card'" class="project-cards">
        <div
          v-for="project in projectBuilds"
          :key="project.projectId"
          class="project-card"
        >
          <!-- 프로젝트 정보 헤더 -->
          <div class="project-header">
            <div class="project-info">
              <h3
                class="project-title"
                @click="toggleProject(project.projectId)"
              >
                <span class="expand-icon">
                  {{ isProjectExpanded(project.projectId) ? '🔽' : '▶️' }}
                </span>
                <span class="project-key">{{ project.projectKey }}</span>
                <span class="project-name">{{ project.title }}</span>
              </h3>
              <p class="project-description">{{ project.description || '설명 없음' }}</p>
            </div>
            <div class="project-meta">
              <span class="project-id">ID: {{ project.projectId }}</span>
              <span class="build-count">빌드: {{ project.builds.length }}개</span>
            </div>
          </div>

          <!-- 빌드 목록 (접기/펼치기 가능) -->
          <div
            v-show="isProjectExpanded(project.projectId)"
            class="builds-section"
            :class="{ 'expanded': isProjectExpanded(project.projectId) }"
          >
            <h4 class="builds-title">빌드 목록</h4>
            <div v-if="project.builds.length === 0" class="no-builds">
              등록된 빌드가 없습니다
            </div>
            <div v-else class="builds-list">
              <div
                v-for="build in project.builds"
                :key="build.buildId"
                class="build-item"
              >
                <div class="build-info">
                  <div class="build-title">{{ build.buildTitle }}</div>
                  <div class="build-meta">
                    <span class="build-type">{{ build.buildType }}</span>
                    <span class="build-id">ID: {{ build.buildId }}</span>
                  </div>
                </div>
                <div class="build-dates">
                  <div class="date-item">
                    <span class="date-label">생성:</span>
                    <span class="date-value">{{ formatDate(build.createdAt) }}</span>
                  </div>
                  <div class="date-item">
                    <span class="date-label">수정:</span>
                    <span class="date-value">{{ formatDate(build.updatedAt) }}</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 테이블 뷰 -->
      <div v-else class="table-container">
        <table class="data-table">
          <thead>
          <tr>
            <th>프로젝트 ID</th>
            <th>프로젝트 키</th>
            <th>프로젝트 제목</th>
            <th>빌드 ID</th>
            <th>빌드 제목</th>
            <th>빌드 타입</th>
            <th>생성일시</th>
            <th>수정일시</th>
          </tr>
          </thead>
          <tbody>
          <template v-for="project in projectBuilds" :key="project.projectId">
            <tr v-if="project.builds.length === 0">
              <td>{{ project.projectId }}</td>
              <td><strong>{{ project.projectKey }}</strong></td>
              <td>{{ project.title }}</td>
              <td class="empty-cell">-</td>
              <td class="empty-cell">빌드 없음</td>
              <td class="empty-cell">-</td>
              <td class="empty-cell">-</td>
              <td class="empty-cell">-</td>
            </tr>
            <tr
              v-else
              v-for="(build, index) in project.builds"
              :key="`${project.projectId}-${build.buildId}`"
            >
              <td v-if="index === 0" :rowspan="project.builds.length">
                {{ project.projectId }}
              </td>
              <td v-if="index === 0" :rowspan="project.builds.length">
                <strong>{{ project.projectKey }}</strong>
              </td>
              <td v-if="index === 0" :rowspan="project.builds.length">
                {{ project.title }}
              </td>
              <td>{{ build.buildId }}</td>
              <td>{{ build.buildTitle }}</td>
              <td>
                <span class="build-type-badge">{{ build.buildType }}</span>
              </td>
              <td>{{ formatDate(build.createdAt) }}</td>
              <td>{{ formatDate(build.updatedAt) }}</td>
            </tr>
          </template>
          </tbody>
        </table>
      </div>
    </div>

    <!-- Excel 다운로드 성공 알림 -->
    <div v-if="downloadSuccess" class="download-success">
      <div class="success-message">
        <span class="success-icon">✅</span>
        <span>Excel 파일이 성공적으로 다운로드되었습니다!</span>
        <button @click="downloadSuccess = false" class="close-btn">×</button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { ProjectService } from '@/services/projectService'
import type { ProjectBuildResponse } from '@/types/api'

// 반응형 데이터
const projectBuilds = ref<ProjectBuildResponse[]>([])
const loading = ref(false)
const error = ref<string | null>(null)
const searchProjectKey = ref('')
const searchProjectId = ref<number | null>(null)
const viewMode = ref<'card' | 'table'>('card')
const downloadSuccess = ref(false)
const expandedProjects = ref<Set<number>>(new Set())

// 통계 계산
const stats = computed(() => {
  if (projectBuilds.value.length === 0) return null

  const totalProjects = projectBuilds.value.length
  const totalBuilds = projectBuilds.value.reduce((sum, project) => sum + project.builds.length, 0)
  const avgBuildsPerProject = totalBuilds > 0 ? (totalBuilds / totalProjects).toFixed(1) : '0'
  const projectsWithoutBuilds = projectBuilds.value.filter(project => project.builds.length === 0).length

  return {
    totalProjects,
    totalBuilds,
    avgBuildsPerProject,
    projectsWithoutBuilds
  }
})

// 프로젝트 확장/축소 토글
const toggleProject = (projectId: number) => {
  if (expandedProjects.value.has(projectId)) {
    expandedProjects.value.delete(projectId)
  } else {
    expandedProjects.value.add(projectId)
  }
}

// 모든 프로젝트 확장/축소
const toggleAllProjects = (expand: boolean) => {
  if (expand) {
    projectBuilds.value.forEach(project => {
      expandedProjects.value.add(project.projectId)
    })
  } else {
    expandedProjects.value.clear()
  }
}

// 프로젝트 확장 상태 확인
const isProjectExpanded = (projectId: number) => {
  return expandedProjects.value.has(projectId)
}

// 날짜 포맷팅 함수
const formatDate = (dateString: string): string => {
  if (!dateString) return '-'
  try {
    return new Date(dateString).toLocaleString('ko-KR', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit'
    })
  } catch {
    return dateString
  }
}

// 에러 처리 헬퍼
const handleError = (err: any, operation: string) => {
  console.error(`❌ ${operation} 실패:`, err)
  error.value = `${operation}에 실패했습니다. ${err.response?.data?.message || err.message || '알 수 없는 오류가 발생했습니다.'}`
  loading.value = false
}

// 성공 처리 헬퍼
const handleSuccess = (data: any, operation: string) => {
  console.log(`✅ ${operation} 성공:`, data)
  error.value = null
  loading.value = false
}

// 모든 프로젝트-빌드 조회
const loadAllProjects = async () => {
  loading.value = true
  error.value = null
  searchProjectKey.value = ''
  searchProjectId.value = null

  try {
    console.log('🚀 Loading all project builds...')
    const data = await ProjectService.getAllProjectsWithBuilds()
    projectBuilds.value = data

    // 초기 로딩 시 모든 프로젝트 펼치기
    expandedProjects.value.clear()
    data.forEach(project => {
      expandedProjects.value.add(project.projectId)
    })

    handleSuccess(data, '전체 프로젝트-빌드 조회')
    console.log(`✅ Loaded ${data.length} projects with builds`)
  } catch (err) {
    handleError(err, '전체 프로젝트-빌드 조회')
    projectBuilds.value = []
  }
}

// 프로젝트 키로 검색
const searchByProjectKey = async (projectKey: string) => {
  loading.value = true
  error.value = null

  try {
    console.log(`🔍 Searching project builds for key: ${projectKey}`)
    const data = await ProjectService.getProjectWithBuildsByKey(projectKey)
    projectBuilds.value = [data]

    // 검색 결과는 기본적으로 펼쳐진 상태
    expandedProjects.value.clear()
    expandedProjects.value.add(data.projectId)

    handleSuccess(data, `프로젝트 키 "${projectKey}" 검색`)
    console.log(`✅ Found project builds for key: ${projectKey}`)
  } catch (err) {
    handleError(err, `프로젝트 키 "${projectKey}" 검색`)
    projectBuilds.value = []
  }
}

// 프로젝트 ID로 검색
const searchByProjectId = async (projectId: number) => {
  loading.value = true
  error.value = null

  try {
    console.log(`🔍 Searching project builds for ID: ${projectId}`)
    const data = await ProjectService.getProjectWithBuildsById(projectId)
    projectBuilds.value = [data]

    // 검색 결과는 기본적으로 펼쳐진 상태
    expandedProjects.value.clear()
    expandedProjects.value.add(data.projectId)

    handleSuccess(data, `프로젝트 ID "${projectId}" 검색`)
    console.log(`✅ Found project builds for ID: ${projectId}`)
  } catch (err) {
    handleError(err, `프로젝트 ID "${projectId}" 검색`)
    projectBuilds.value = []
  }
}

// 검색 핸들러
const handleSearch = () => {
  const key = searchProjectKey.value.trim()
  if (key) {
    searchProjectId.value = null // ID 검색 초기화
    searchByProjectKey(key)
  } else {
    loadAllProjects()
  }
}

// ID 검색 핸들러
const handleSearchById = () => {
  if (searchProjectId.value) {
    searchProjectKey.value = '' // 키 검색 초기화
    searchByProjectId(searchProjectId.value)
  }
}

// 초기화 핸들러
const handleReset = () => {
  searchProjectKey.value = ''
  searchProjectId.value = null
  loadAllProjects()
}

// 재시도 핸들러
const retryLoad = () => {
  if (searchProjectKey.value.trim()) {
    handleSearch()
  } else if (searchProjectId.value) {
    handleSearchById()
  } else {
    loadAllProjects()
  }
}

// Excel 다운로드
const downloadExcel = async () => {
  if (projectBuilds.value.length === 0) {
    error.value = '다운로드할 데이터가 없습니다.'
    return
  }

  loading.value = true
  try {
    console.log('📊 Starting Excel download...')
    const blob = await ProjectService.downloadProjectBuildsExcel()

    // 파일 다운로드
    const url = window.URL.createObjectURL(blob)
    const link = document.createElement('a')
    link.href = url
    link.download = `project-builds-${new Date().toISOString().slice(0, 19).replace(/[:]/g, '-')}.xlsx`
    document.body.appendChild(link)
    link.click()
    document.body.removeChild(link)
    window.URL.revokeObjectURL(url)

    downloadSuccess.value = true
    setTimeout(() => {
      downloadSuccess.value = false
    }, 5000) // 5초 후 자동 숨김

    handleSuccess(blob, 'Excel 다운로드')
    console.log('✅ Excel download completed successfully')
  } catch (err) {
    handleError(err, 'Excel 다운로드')
  }
}

// 컴포넌트 마운트 시 데이터 로드
onMounted(() => {
  loadAllProjects()
})
</script>

<style scoped>
.project-builds {
  max-width: 1400px;
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

/* 검색 섹션 */
.search-section {
  background: white;
  padding: 24px;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  margin-bottom: 24px;
}

.search-container {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.search-group {
  flex: 1;
}

.search-label {
  display: block;
  font-weight: 600;
  color: #2c3e50;
  margin-bottom: 8px;
}

.search-input-group {
  display: flex;
  gap: 12px;
  align-items: center;
  flex-wrap: wrap;
}

.search-input {
  flex: 1;
  min-width: 300px;
  padding: 12px 16px;
  border: 2px solid #e1e8ed;
  border-radius: 8px;
  font-size: 16px;
  transition: border-color 0.2s ease;
}

.search-input:focus {
  outline: none;
  border-color: #3498db;
  box-shadow: 0 0 0 3px rgba(52, 152, 219, 0.1);
}

.search-btn, .reset-btn {
  padding: 12px 20px;
  border: none;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s ease;
  white-space: nowrap;
}

.search-btn {
  background: #3498db;
  color: white;
}

.search-btn:hover:not(:disabled) {
  background: #2980b9;
  transform: translateY(-1px);
}

.reset-btn {
  background: #95a5a6;
  color: white;
}

.reset-btn:hover:not(:disabled) {
  background: #7f8c8d;
  transform: translateY(-1px);
}

.search-btn:disabled, .reset-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
  transform: none;
}

.action-buttons {
  display: flex;
  gap: 12px;
  flex-wrap: wrap;
}

.action-btn {
  padding: 12px 20px;
  border: none;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s ease;
  white-space: nowrap;
}

.action-btn.primary {
  background: #27ae60;
  color: white;
}

.action-btn.primary:hover:not(:disabled) {
  background: #219a52;
  transform: translateY(-1px);
}

.action-btn.secondary {
  background: #e67e22;
  color: white;
}

.action-btn.secondary:hover:not(:disabled) {
  background: #d35400;
  transform: translateY(-1px);
}

.action-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
  transform: none;
}

/* 통계 섹션 */
.stats-section {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
  margin-bottom: 24px;
}

.stat-card {
  background: white;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: center;
  gap: 16px;
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.stat-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
}

.stat-icon {
  font-size: 32px;
  width: 50px;
  height: 50px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.stat-content {
  flex: 1;
}

.stat-value {
  font-size: 24px;
  font-weight: 700;
  color: #2c3e50;
  margin: 0;
}

.stat-label {
  font-size: 14px;
  color: #7f8c8d;
  margin: 4px 0 0 0;
}

/* 상태 섹션들 */
.loading-section, .error-section, .empty-section {
  background: white;
  padding: 60px 40px;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.loading-spinner, .error-icon, .empty-icon {
  font-size: 48px;
  margin-bottom: 16px;
}

.loading-spinner {
  animation: spin 1s linear infinite;
}

@keyframes spin {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}

.loading-text, .error-title, .empty-title {
  font-size: 20px;
  font-weight: 600;
  color: #2c3e50;
  margin: 0 0 8px 0;
}

.error-message, .empty-message {
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

/* 컨텐츠 섹션 */
.content-section {
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.results-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 24px;
  border-bottom: 1px solid #e1e8ed;
  background: #f8f9fa;
}

.results-title {
  font-size: 20px;
  font-weight: 600;
  color: #2c3e50;
  margin: 0;
}

.view-options {
  display: flex;
  gap: 8px;
  align-items: center;
  flex-wrap: wrap;
}

.expand-controls {
  display: flex;
  gap: 8px;
  margin-left: 16px;
  padding-left: 16px;
  border-left: 1px solid #e1e8ed;
}

.expand-btn {
  padding: 6px 12px;
  border: 1px solid #e1e8ed;
  background: white;
  border-radius: 4px;
  font-size: 12px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
  color: #666;
}

.expand-btn:hover:not(:disabled) {
  border-color: #3498db;
  color: #3498db;
  background: #f8f9fa;
}

.expand-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.view-btn {
  padding: 8px 16px;
  border: 2px solid #e1e8ed;
  background: white;
  border-radius: 6px;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s ease;
}

.view-btn.active {
  background: #3498db;
  border-color: #3498db;
  color: white;
}

.view-btn:not(.active):hover {
  border-color: #3498db;
  color: #3498db;
}

/* 카드 뷰 */
.project-cards {
  padding: 24px;
  display: grid;
  gap: 24px;
}

.project-card {
  border: 1px solid #e1e8ed;
  border-radius: 12px;
  overflow: hidden;
  transition: box-shadow 0.2s ease, transform 0.2s ease;
}

.project-card:hover {
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
  transform: translateY(-2px);
}

.project-header {
  background: #f8f9fa;
  padding: 20px;
  border-bottom: 1px solid #e1e8ed;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}

.project-info {
  flex: 1;
}

.project-title {
  font-size: 18px;
  font-weight: 600;
  color: #2c3e50;
  margin: 0 0 8px 0;
  display: flex;
  align-items: center;
  gap: 12px;
  flex-wrap: wrap;
  cursor: pointer;
  transition: all 0.2s ease;
  user-select: none;
  padding: 8px;
  border-radius: 8px;
}

.project-title:hover {
  background: rgba(52, 152, 219, 0.1);
}

.expand-icon {
  font-size: 14px;
  transition: transform 0.2s ease;
  color: #3498db;
  min-width: 16px;
}

.project-key {
  background: #3498db;
  color: white;
  padding: 4px 12px;
  border-radius: 6px;
  font-size: 14px;
  font-weight: 700;
}

.project-name {
  color: #2c3e50;
}

.project-description {
  font-size: 14px;
  color: #7f8c8d;
  margin: 0;
  line-height: 1.4;
}

.project-meta {
  text-align: right;
  font-size: 12px;
  color: #95a5a6;
}

.project-id, .build-count {
  display: block;
  margin-bottom: 4px;
}

.builds-section {
  padding: 20px;
  animation: slideDown 0.3s ease-out;
  overflow: hidden;
}

@keyframes slideDown {
  from {
    opacity: 0;
    max-height: 0;
    padding-top: 0;
    padding-bottom: 0;
  }
  to {
    opacity: 1;
    max-height: 1000px;
    padding-top: 20px;
    padding-bottom: 20px;
  }
}

.builds-title {
  font-size: 16px;
  font-weight: 600;
  color: #2c3e50;
  margin: 0 0 16px 0;
}

.no-builds {
  text-align: center;
  color: #7f8c8d;
  font-style: italic;
  padding: 20px;
  background: #f8f9fa;
  border-radius: 8px;
}

.builds-list {
  display: grid;
  gap: 12px;
}

.build-item {
  background: #f8f9fa;
  padding: 16px;
  border-radius: 8px;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  border-left: 4px solid #3498db;
  transition: background 0.2s ease;
}

.build-item:hover {
  background: #ecf0f1;
}

.build-info {
  flex: 1;
}

.build-title {
  font-size: 14px;
  font-weight: 600;
  color: #2c3e50;
  margin-bottom: 4px;
}

.build-meta {
  display: flex;
  gap: 12px;
  font-size: 12px;
  color: #7f8c8d;
  flex-wrap: wrap;
}

.build-type {
  background: #e67e22;
  color: white;
  padding: 2px 8px;
  border-radius: 4px;
  font-weight: 600;
}

.build-dates {
  text-align: right;
  font-size: 12px;
}

.date-item {
  display: flex;
  justify-content: space-between;
  gap: 8px;
  margin-bottom: 2px;
}

.date-label {
  color: #7f8c8d;
  font-weight: 500;
}

.date-value {
  color: #2c3e50;
}

/* 테이블 뷰 */
.table-container {
  overflow-x: auto;
}

.data-table {
  width: 100%;
  border-collapse: collapse;
  font-size: 14px;
}

.data-table th {
  background: #f8f9fa;
  padding: 16px 12px;
  text-align: left;
  font-weight: 600;
  color: #2c3e50;
  border-bottom: 2px solid #e1e8ed;
  white-space: nowrap;
}

.data-table td {
  padding: 12px;
  border-bottom: 1px solid #e1e8ed;
  vertical-align: top;
}

.data-table tbody tr:hover {
  background: #f8f9fa;
}

.empty-cell {
  color: #7f8c8d;
  font-style: italic;
  text-align: center;
}

.build-type-badge {
  background: #e67e22;
  color: white;
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 12px;
  font-weight: 600;
}

/* Excel 다운로드 성공 알림 */
.download-success {
  position: fixed;
  top: 20px;
  right: 20px;
  z-index: 1000;
  animation: slideIn 0.3s ease-out;
}

@keyframes slideIn {
  from {
    transform: translateX(100%);
    opacity: 0;
  }
  to {
    transform: translateX(0);
    opacity: 1;
  }
}

.success-message {
  background: #27ae60;
  color: white;
  padding: 16px 20px;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(39, 174, 96, 0.3);
  display: flex;
  align-items: center;
  gap: 12px;
  max-width: 350px;
}

.success-icon {
  font-size: 20px;
}

.close-btn {
  background: none;
  border: none;
  color: white;
  font-size: 20px;
  cursor: pointer;
  padding: 0;
  width: 24px;
  height: 24px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  transition: background 0.2s ease;
}

.close-btn:hover {
  background: rgba(255, 255, 255, 0.2);
}

/* 반응형 디자인 */
@media (max-width: 768px) {
  .project-builds {
    padding: 12px;
  }

  .search-container {
    flex-direction: column;
  }

  .search-input-group {
    flex-direction: column;
    align-items: stretch;
  }

  .search-input {
    min-width: auto;
  }

  .action-buttons {
    justify-content: stretch;
  }

  .action-btn {
    flex: 1;
  }

  .results-header {
    flex-direction: column;
    gap: 16px;
    align-items: stretch;
  }

  .view-options {
    flex-direction: column;
    align-items: stretch;
    gap: 12px;
  }

  .expand-controls {
    margin-left: 0;
    padding-left: 0;
    border-left: none;
    border-top: 1px solid #e1e8ed;
    padding-top: 12px;
    justify-content: center;
  }

  .project-header {
    flex-direction: column;
    gap: 12px;
  }

  .project-meta {
    text-align: left;
  }

  .project-title {
    flex-direction: column;
    align-items: flex-start;
    gap: 8px;
    padding: 4px;
  }

  .build-item {
    flex-direction: column;
    gap: 12px;
  }

  .build-dates {
    text-align: left;
  }

  .build-meta {
    flex-direction: column;
    gap: 4px;
  }

  .download-success {
    top: 10px;
    right: 10px;
    left: 10px;
  }

  .success-message {
    max-width: none;
  }

  .data-table {
    font-size: 12px;
  }

  .data-table th,
  .data-table td {
    padding: 8px 6px;
  }
}

@media (max-width: 480px) {
  .page-title {
    font-size: 24px;
  }

  .loading-bar {
    width: 200px;
  }

  .stats-section {
    grid-template-columns: repeat(2, 1fr);
  }

  .stat-card {
    padding: 16px;
    flex-direction: column;
    text-align: center;
    gap: 8px;
  }

  .stat-icon {
    width: 40px;
    height: 40px;
    font-size: 24px;
  }

  .stat-value {
    font-size: 20px;
  }
}
</style>
