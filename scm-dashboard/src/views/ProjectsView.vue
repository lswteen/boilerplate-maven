<template>
  <div class="projects-view">
    <!-- 페이지 헤더 -->
    <div class="page-header">
      <div class="header-left">
        <h1 class="page-title">프로젝트 관리</h1>
        <p class="page-description">
          SCM 프로젝트와 프로젝트관리 매핑 현황을 관리합니다
        </p>
      </div>
      <div class="header-actions">
        <button class="btn btn-primary" @click="refreshData">
        새로고침
        </button>
        <button class="btn btn-success" @click="downloadExcel">
        Excel 다운로드
        </button>
      </div>
    </div>

    <!-- 탭 네비게이션 -->
    <div class="tab-navigation">
      <button
        class="tab-btn"
        :class="{ 'active': activeTab === 'mapped' }"
        @click="activeTab = 'mapped'"
      >
      Mapping 프로젝트 ({{ mappedProjects.length }})
      </button>
      <button
        class="tab-btn"
        :class="{ 'active': activeTab === 'unmapped' }"
        @click="activeTab = 'unmapped'"
      >
      UnMapping 프로젝트 ({{ unmappedProjects.length }})
      </button>
    </div>

    <!-- 검색 및 필터 -->
    <div class="search-section">
      <div class="search-controls">
        <div class="search-input-group">
          <input
            type="text"
            v-model="searchQuery"
            placeholder="프로젝트 키 또는 제목으로 검색..."
            class="search-input"
          />
          <button class="search-btn">검색</button>
        </div>
<!--        <select v-model="statusFilter" class="filter-select">-->
<!--          <option value="">전체 상태</option>-->
<!--          <option value="ACTIVE">활성</option>-->
<!--          <option value="INACTIVE">비활성</option>-->
<!--          <option value="PENDING">대기</option>-->
<!--        </select>-->
      </div>
    </div>

    <!-- 로딩 상태 -->
    <div v-if="loading" class="loading-section">
      <div class="loading-spinner">⏳</div>
      <p>데이터를 불러오는 중...</p>
    </div>

    <!-- 에러 상태 -->
    <div v-else-if="error" class="error-section">
      <div class="error-icon">❌</div>
      <p class="error-message">{{ error }}</p>
      <button class="btn btn-primary" @click="loadData">다시 시도</button>
    </div>

    <!-- 매핑된 프로젝트 테이블 -->
    <div v-else-if="activeTab === 'mapped'" class="table-section">
      <div class="table-header">
<!--        <h3>🔗 프로젝트-프로젝트관리 매핑 현황</h3>-->
        <div class="table-info">
        총 {{ filteredMappedProjects.length }}개 프로젝트
        </div>
      </div>

      <div class="table-container">
        <table class="data-table">
          <thead>
          <tr>
            <th>프로젝트 키</th>
            <th>프로젝트 제목</th>
            <th>비즈 구분</th>
            <th>비즈 매니저</th>
            <th>개발자</th>
            <th>상태</th>
            <th>Bamboo 키</th>
            <th>생성일</th>
            <th>액션</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="project in paginatedMappedProjects" :key="project.projectId">
            <td>
              <span class="project-key">{{ project.projectKey }}</span>
            </td>
            <td>
              <div class="project-title">{{ project.title }}</div>
              <div class="project-desc">{{ project.description || '설명 없음' }}</div>
            </td>
            <td>
              <span class="biz-div">{{ project.bizDiv }}</span>
            </td>
            <td>
              <span class="biz-mgr">{{ project.bizMgr }}</span>
            </td>
            <td>
              <span class="developer">{{ project.dev }}</span>
            </td>
            <td>
                <span class="status-badge" :class="getStatusClass(project.status)">
                  {{ getStatusText(project.status) }}
                </span>
            </td>
            <td>
              <span class="bamboo-key">{{ project.bambooKey || 'N/A' }}</span>
            </td>
            <td>
              <span class="created-date">{{ formatDate(project.createdAt) }}</span>
            </td>
            <td>
              <div class="action-buttons">
                <button class="btn-icon" title="상세보기" @click="viewDetail(project)">
                상세
                </button>
                <button class="btn-icon" title="수정" @click="editProject(project)">
                수정
                </button>
              </div>
            </td>
          </tr>
          </tbody>
        </table>
      </div>

      <!-- 페이지네이션 -->
      <div class="pagination">
        <button
          class="pagination-btn"
          :disabled="currentPage === 1"
          @click="currentPage--"
        >
          ◀ 이전
        </button>

        <span class="pagination-info">
          {{ currentPage }} / {{ totalPages }} 페이지
          ({{ filteredMappedProjects.length }}개 항목)
        </span>

        <button
          class="pagination-btn"
          :disabled="currentPage === totalPages"
          @click="currentPage++"
        >
          다음 ▶
        </button>
      </div>
    </div>

    <!-- 미매핑 프로젝트 테이블 -->
    <div v-else-if="activeTab === 'unmapped'" class="table-section">
      <div class="table-header">
        <h3>프로젝트관리 미매핑 프로젝트</h3>
        <div class="table-info">
          총 {{ unmappedProjects.length }}개 프로젝트 (매핑 필요)
        </div>
      </div>

      <div class="table-container">
        <table class="data-table">
          <thead>
          <tr>
            <th>프로젝트 키</th>
            <th>프로젝트 제목</th>
            <th>설명</th>
            <th>빌드 수</th>
            <th>액션</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="project in unmappedProjects" :key="project.projectId">
            <td>
              <span class="project-key unmapped">{{ project.projectKey }}</span>
            </td>
            <td>
              <div class="project-title">{{ project.title }}</div>
            </td>
            <td>
              <div class="project-desc">{{ project.description || '설명 없음' }}</div>
            </td>
            <td>
              <span class="build-count">{{ project.builds?.length || 0 }}개</span>
            </td>
            <td>
              <div class="action-buttons">
                <button class="btn btn-sm btn-primary" @click="mapProject(project)">
                매핑하기
                </button>
              </div>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import ProjectService from '@/services/projectService'
import type { ProjectProjMgmtResponse, ProjectResponse } from '@/types/api'

// 반응형 상태
const loading = ref(true)
const error = ref('')
const activeTab = ref<'mapped' | 'unmapped'>('mapped')
const searchQuery = ref('')
const statusFilter = ref('')
const currentPage = ref(1)
const itemsPerPage = ref(10)

// 데이터 상태
const mappedProjects = ref<ProjectProjMgmtResponse[]>([])
const unmappedProjects = ref<ProjectResponse[]>([])

// 계산된 속성
const filteredMappedProjects = computed(() => {
  let filtered = mappedProjects.value

  // 검색 필터
  if (searchQuery.value) {
    const query = searchQuery.value.toLowerCase()
    filtered = filtered.filter(project =>
      project.projectKey.toLowerCase().includes(query) ||
      project.title.toLowerCase().includes(query)
    )
  }

  // 상태 필터
  if (statusFilter.value) {
    filtered = filtered.filter(project => project.status === statusFilter.value)
  }

  return filtered
})

const totalPages = computed(() => {
  return Math.ceil(filteredMappedProjects.value.length / itemsPerPage.value)
})

const paginatedMappedProjects = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage.value
  const end = start + itemsPerPage.value
  return filteredMappedProjects.value.slice(start, end)
})

// 메서드
const loadData = async () => {
  loading.value = true
  error.value = ''

  try {
    // 매핑된 프로젝트 조회
    mappedProjects.value = await ProjectService.getAllProjectsWithProjMgmt()

    // 미매핑 프로젝트 조회
    unmappedProjects.value = await ProjectService.getUnmappedProjects()

    console.log('✅ 프로젝트 데이터 로드 완료:', {
      mapped: mappedProjects.value.length,
      unmapped: unmappedProjects.value.length
    })
  } catch (err: any) {
    console.error('❌ 프로젝트 데이터 로드 실패:', err)
    error.value = `데이터 로드 실패: ${err.message}`
  } finally {
    loading.value = false
  }
}

const refreshData = async () => {
  await loadData()
}

const downloadExcel = async () => {
  try {
    const blob = await ProjectService.downloadProjectProjMgmtExcel()

    // 파일 다운로드
    const url = window.URL.createObjectURL(blob)
    const link = document.createElement('a')
    link.href = url
    link.download = `project-projmgmt-${new Date().toISOString().slice(0, 10)}.xlsx`
    document.body.appendChild(link)
    link.click()
    document.body.removeChild(link)
    window.URL.revokeObjectURL(url)
  } catch (err: any) {
    console.error('Excel 다운로드 실패:', err)
    alert('Excel 다운로드에 실패했습니다.')
  }
}

const viewDetail = (project: ProjectProjMgmtResponse) => {
  // TODO: 상세보기 모달 구현
  console.log('상세보기:', project)
  alert(`${project.title} 상세보기 (구현 예정)`)
}

const editProject = (project: ProjectProjMgmtResponse) => {
  // TODO: 수정 모달 구현
  console.log('수정:', project)
  alert(`${project.title} 수정 (구현 예정)`)
}

const mapProject = (project: ProjectResponse) => {
  // TODO: 매핑 모달 구현
  console.log('매핑:', project)
  alert(`${project.title} 매핑 (구현 예정)`)
}

// 유틸리티 함수
const getStatusClass = (status: string) => {
  switch (status?.toLowerCase()) {
    case 'active': return 'status-active'
    case 'inactive': return 'status-inactive'
    case 'pending': return 'status-pending'
    default: return 'status-unknown'
  }
}

const getStatusText = (status: string) => {
  switch (status?.toLowerCase()) {
    case 'active': return '활성'
    case 'inactive': return '비활성'
    case 'pending': return '대기'
    default: return '알 수 없음'
  }
}

const formatDate = (dateString: string) => {
  if (!dateString) return 'N/A'
  return new Date(dateString).toLocaleDateString('ko-KR')
}

// 컴포넌트 마운트
onMounted(async () => {
  await loadData()
})
</script>

<style scoped>
.projects-view {
  max-width: 1400px;
  margin: 0 auto;
}

/* 페이지 헤더 */
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 24px;
  padding-bottom: 20px;
  border-bottom: 2px solid #eee;
}

.header-left {
  flex: 1;
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

.header-actions {
  display: flex;
  gap: 12px;
}

/* 탭 네비게이션 */
.tab-navigation {
  display: flex;
  margin-bottom: 24px;
  border-bottom: 2px solid #eee;
}

.tab-btn {
  padding: 12px 24px;
  background: none;
  border: none;
  font-size: 16px;
  font-weight: 600;
  color: #7f8c8d;
  cursor: pointer;
  border-bottom: 3px solid transparent;
  transition: all 0.3s ease;
}

.tab-btn:hover {
  color: #3498db;
  background: rgba(52, 152, 219, 0.1);
}

.tab-btn.active {
  color: #3498db;
  border-bottom-color: #3498db;
  background: rgba(52, 152, 219, 0.1);
}

/* 검색 섹션 */
.search-section {
  margin-bottom: 24px;
}

.search-controls {
  display: flex;
  gap: 16px;
  align-items: center;
}

.search-input-group {
  display: flex;
  flex: 1;
  max-width: 400px;
}

.search-input {
  flex: 1;
  padding: 10px 16px;
  border: 2px solid #ddd;
  border-radius: 8px 0 0 8px;
  font-size: 14px;
  outline: none;
}

.search-input:focus {
  border-color: #3498db;
}

.search-btn {
  padding: 10px 16px;
  background: #3498db;
  color: white;
  border: 2px solid #3498db;
  border-radius: 0 8px 8px 0;
  cursor: pointer;
}

.filter-select {
  padding: 10px 16px;
  border: 2px solid #ddd;
  border-radius: 8px;
  font-size: 14px;
  background: white;
}

/* 로딩 및 에러 */
.loading-section, .error-section {
  text-align: center;
  padding: 40px;
}

.loading-spinner {
  font-size: 48px;
  margin-bottom: 16px;
}

.error-icon {
  font-size: 48px;
  margin-bottom: 16px;
}

.error-message {
  font-size: 16px;
  color: #e74c3c;
  margin-bottom: 16px;
}

/* 테이블 섹션 */
.table-section {
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.table-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 24px;
  background: #f8f9fa;
  border-bottom: 1px solid #ddd;
}

.table-header h3 {
  margin: 0;
  font-size: 18px;
  font-weight: 600;
  color: #2c3e50;
}

.table-info {
  font-size: 14px;
  color: #7f8c8d;
}

.table-container {
  overflow-x: auto;
}

/* 데이터 테이블 */
.data-table {
  width: 100%;
  border-collapse: collapse;
}

.data-table th {
  background: #f1f2f6;
  padding: 16px 12px;
  text-align: left;
  font-weight: 600;
  color: #2c3e50;
  border-bottom: 1px solid #ddd;
  font-size: 14px;
  white-space: nowrap;
}

.data-table td {
  padding: 16px 12px;
  border-bottom: 1px solid #eee;
  vertical-align: top;
}

.data-table tbody tr:hover {
  background: #f8f9fa;
}

/* 테이블 요소 스타일 */
.project-key {
  font-family: 'Monaco', 'Menlo', monospace;
  font-weight: 600;
  color: #3498db;
  font-size: 14px;
}

.project-key.unmapped {
  color: #e74c3c;
}

.project-title {
  font-weight: 600;
  color: #2c3e50;
  margin-bottom: 4px;
}

.project-desc {
  font-size: 12px;
  color: #7f8c8d;
  line-height: 1.4;
}

.biz-div, .biz-mgr, .developer {
  font-size: 14px;
  color: #2c3e50;
}

.status-badge {
  padding: 4px 12px;
  border-radius: 16px;
  font-size: 12px;
  font-weight: 600;
  text-transform: uppercase;
}

.status-active {
  background: #d4edda;
  color: #155724;
}

.status-inactive {
  background: #f8d7da;
  color: #721c24;
}

.status-pending {
  background: #fff3cd;
  color: #856404;
}

.status-unknown {
  background: #e2e3e5;
  color: #6c757d;
}

.bamboo-key {
  font-family: 'Monaco', 'Menlo', monospace;
  font-size: 12px;
  color: #6c757d;
}

.created-date {
  font-size: 12px;
  color: #6c757d;
}

.build-count {
  font-weight: 600;
  color: #3498db;
}

/* 액션 버튼 */
.action-buttons {
  display: flex;
  gap: 8px;
}

.btn-icon {
  padding: 6px 8px;
  background: #f8f9fa;
  border: 1px solid #ddd;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.2s ease;
}

.btn-icon:hover {
  background: #e9ecef;
  transform: scale(1.1);
}

/* 페이지네이션 */
.pagination {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 24px;
  background: #f8f9fa;
}

.pagination-btn {
  padding: 8px 16px;
  background: #3498db;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.2s ease;
}

.pagination-btn:hover:not(:disabled) {
  background: #2980b9;
}

.pagination-btn:disabled {
  background: #bdc3c7;
  cursor: not-allowed;
}

.pagination-info {
  font-size: 14px;
  color: #6c757d;
}

/* 버튼 스타일 */
.btn {
  padding: 10px 20px;
  border: none;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s ease;
  text-decoration: none;
  display: inline-flex;
  align-items: center;
  gap: 8px;
}

.btn-sm {
  padding: 6px 12px;
  font-size: 12px;
}

.btn-primary {
  background: #3498db;
  color: white;
}

.btn-primary:hover {
  background: #2980b9;
  transform: translateY(-1px);
}

.btn-success {
  background: #27ae60;
  color: white;
}

.btn-success:hover {
  background: #229954;
  transform: translateY(-1px);
}

/* 반응형 디자인 */
@media (max-width: 768px) {
  .page-header {
    flex-direction: column;
    gap: 16px;
    align-items: stretch;
  }

  .header-actions {
    justify-content: flex-start;
  }

  .search-controls {
    flex-direction: column;
    gap: 12px;
  }

  .search-input-group {
    max-width: none;
  }

  .table-container {
    font-size: 12px;
  }

  .data-table th,
  .data-table td {
    padding: 8px 6px;
  }

  .pagination {
    flex-direction: column;
    gap: 12px;
  }
}
</style>
