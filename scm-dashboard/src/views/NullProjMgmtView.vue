<!-- src/views/NullProjMgmtView.vue -->
<template>
  <div class="null-projmgmt">
    <!-- 페이지 헤더 -->
    <div class="page-header">
      <h1 class="page-title">NULL 프로젝트 관리</h1>
      <p class="page-description">
        bambookey가 'NULL'인 프로젝트관리 데이터를 조회하고 관리하세요
      </p>
    </div>

    <!-- 검색 및 필터 영역 -->
    <div class="search-section">
      <div class="search-container">
        <div class="search-group">
          <label class="search-label">프로젝트관리 검색</label>
          <div class="search-input-group">
            <input
              v-model="searchKeyword"
              type="text"
              placeholder="프로젝트명, 담당자, 상태 등으로 검색하세요"
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

        <!-- 상태 필터 -->
        <div class="filter-group">
          <label class="search-label">상태 필터</label>
          <div class="filter-buttons">
            <button
              v-for="status in statusFilters"
              :key="status.value"
              @click="setStatusFilter(status.value)"
              class="filter-btn"
              :class="{ active: selectedStatus === status.value }"
            >
              {{ status.label }}
            </button>
          </div>
        </div>

        <!-- 빠른 액션 버튼들 -->
        <div class="action-buttons">
          <button
            @click="loadAllData"
            class="action-btn primary"
            :disabled="loading"
          >
            전체 조회
          </button>
          <button
            @click="downloadExcel"
            class="action-btn secondary"
            :disabled="loading || nullProjMgmtData.length === 0"
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
          <div class="stat-value">{{ stats.totalRecords }}</div>
          <div class="stat-label">총 NULL 레코드</div>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-content">
          <div class="stat-value">{{ stats.uniqueManagers }}</div>
          <div class="stat-label">고유 담당자</div>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-content">
          <div class="stat-value">{{ stats.statusTypes }}</div>
          <div class="stat-label">상태 유형</div>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-content">
          <div class="stat-value">{{ stats.uniqueDivisions }}</div>
          <div class="stat-label">사업부</div>
        </div>
      </div>
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
      <button @click="retryLoad" class="retry-btn">다시 시도</button>
    </div>

    <!-- 데이터 없음 상태 -->
    <div v-else-if="filteredData.length === 0 && !loading" class="empty-section">
      <h3 class="empty-title">조회 결과가 없습니다</h3>
      <p class="empty-message">
        {{ searchKeyword || selectedStatus !== 'all' ?
        `검색 조건에 해당하는 데이터를 찾을 수 없습니다.` :
        '등록된 NULL 프로젝트관리 데이터가 없습니다.'
        }}
      </p>
    </div>

    <!-- NULL 프로젝트관리 목록 -->
    <div v-else class="content-section">
      <div class="results-header">
        <h2 class="results-title">
          조회 결과 ({{ filteredData.length }}개 레코드)
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
        </div>
      </div>

      <!-- 카드 뷰 -->
      <div v-if="viewMode === 'card'" class="projmgmt-cards">
        <div
          v-for="projmgmt in filteredData"
          :key="projmgmt.id"
          class="projmgmt-card"
        >
          <!-- 프로젝트관리 정보 헤더 -->
          <div class="projmgmt-header">
            <div class="projmgmt-info">
              <h3 class="projmgmt-title">
                <span class="projmgmt-id">ID: {{ projmgmt.id }}</span>
                <span class="projmgmt-status" :class="getStatusClass(projmgmt.status)">
                  {{ projmgmt.status }}
                </span>
              </h3>
              <div class="projmgmt-basic-info">
                <div class="info-row">
                  <span class="info-label">사업부:</span>
                  <span class="info-value">{{ projmgmt.bizDiv || '-' }}</span>
                </div>
                <div class="info-row">
                  <span class="info-label">사업관리자:</span>
                  <span class="info-value">{{ projmgmt.bizMgr || '-' }}</span>
                </div>
              </div>
            </div>
            <div class="projmgmt-meta">
              <div class="meta-item">
                <span class="meta-label">생성일</span>
                <span class="meta-value">{{ formatDate(projmgmt.createdAt) }}</span>
              </div>
              <div class="meta-item">
                <span class="meta-label">수정일</span>
                <span class="meta-value">{{ formatDate(projmgmt.updatedAt) }}</span>
              </div>
            </div>
          </div>

          <!-- 상세 정보 섹션 -->
          <div class="projmgmt-details">
            <div class="details-grid">
              <div class="detail-group">
                <h4 class="group-title">담당자 정보</h4>
                <div class="detail-item">
                  <span class="detail-label">개발:</span>
                  <span class="detail-value">{{ projmgmt.dev || '-' }}</span>
                </div>
                <div class="detail-item">
                  <span class="detail-label">운영:</span>
                  <span class="detail-value">{{ projmgmt.oper || '-' }}</span>
                </div>
                <div class="detail-item">
                  <span class="detail-label">배포관리자:</span>
                  <span class="detail-value">{{ projmgmt.deployMgr || '-' }}</span>
                </div>
              </div>

              <div class="detail-group">
                <h4 class="group-title">Bitbucket 정보</h4>
                <div class="detail-item">
                  <span class="detail-label">이름:</span>
                  <span class="detail-value">{{ projmgmt.bitbucketName || '-' }}</span>
                </div>
                <div class="detail-item">
                  <span class="detail-label">키:</span>
                  <span class="detail-value">{{ projmgmt.bitbucketKey || '-' }}</span>
                </div>
                <div class="detail-item">
                  <span class="detail-label">설명:</span>
                  <span class="detail-value">{{ projmgmt.bitbucketDesc || '-' }}</span>
                </div>
              </div>

              <div class="detail-group">
                <h4 class="group-title">Bamboo 정보</h4>
                <div class="detail-item">
                  <span class="detail-label">이름:</span>
                  <span class="detail-value">{{ projmgmt.bambooName || '-' }}</span>
                </div>
                <div class="detail-item">
                  <span class="detail-label">키:</span>
                  <span class="detail-value bamboo-key-null">{{ projmgmt.bambooKey || 'NULL' }}</span>
                </div>
                <div class="detail-item">
                  <span class="detail-label">설명:</span>
                  <span class="detail-value">{{ projmgmt.bambooDesc || '-' }}</span>
                </div>
              </div>

              <div class="detail-group">
                <h4 class="group-title">기타 정보</h4>
                <div class="detail-item">
                  <span class="detail-label">설정:</span>
                  <span class="detail-value">{{ projmgmt.config || '-' }}</span>
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
            <th>ID</th>
            <th>사업부</th>
            <th>사업관리자</th>
            <th>상태</th>
            <th>개발담당</th>
            <th>운영담당</th>
            <th>Bitbucket명</th>
            <th>Bamboo키</th>
            <th>배포관리자</th>
            <th>생성일</th>
            <th>수정일</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="projmgmt in filteredData" :key="projmgmt.id">
            <td><strong>{{ projmgmt.id }}</strong></td>
            <td>{{ projmgmt.bizDiv || '-' }}</td>
            <td>{{ projmgmt.bizMgr || '-' }}</td>
            <td>
                <span class="status-badge" :class="getStatusClass(projmgmt.status)">
                  {{ projmgmt.status }}
                </span>
            </td>
            <td>{{ projmgmt.dev || '-' }}</td>
            <td>{{ projmgmt.oper || '-' }}</td>
            <td>{{ projmgmt.bitbucketName || '-' }}</td>
            <td class="bamboo-key-null">{{ projmgmt.bambooKey || 'NULL' }}</td>
            <td>{{ projmgmt.deployMgr || '-' }}</td>
            <td>{{ formatDate(projmgmt.createdAt) }}</td>
            <td>{{ formatDate(projmgmt.updatedAt) }}</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- Excel 다운로드 성공 알림 -->
    <div v-if="downloadSuccess" class="download-success">
      <div class="success-message">
        <span>Excel 파일이 성공적으로 다운로드되었습니다!</span>
        <button @click="downloadSuccess = false" class="close-btn">×</button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { ProjectService } from '@/services/projectService'
import type { ProjMgmtDto } from '@/types/api'

// 반응형 데이터
const nullProjMgmtData = ref<ProjMgmtDto[]>([])
const loading = ref(false)
const error = ref<string | null>(null)
const searchKeyword = ref('')
const selectedStatus = ref('all')
const viewMode = ref<'card' | 'table'>('card')
const downloadSuccess = ref(false)

// 상태 필터 옵션
const statusFilters = [
  { value: 'all', label: '전체' },
  { value: 'active', label: '활성' },
  { value: 'inactive', label: '비활성' },
  { value: 'pending', label: '대기' },
  { value: 'maintenance', label: '유지보수' }
]

// 통계 계산
const stats = computed(() => {
  if (nullProjMgmtData.value.length === 0) return null

  const totalRecords = nullProjMgmtData.value.length
  const uniqueManagers = new Set(nullProjMgmtData.value.map(item => item.bizMgr).filter(Boolean)).size
  const statusTypes = new Set(nullProjMgmtData.value.map(item => item.status).filter(Boolean)).size
  const uniqueDivisions = new Set(nullProjMgmtData.value.map(item => item.bizDiv).filter(Boolean)).size

  return {
    totalRecords,
    uniqueManagers,
    statusTypes,
    uniqueDivisions
  }
})

// 필터링된 데이터
const filteredData = computed(() => {
  let data = nullProjMgmtData.value

  // 검색 키워드 필터링
  if (searchKeyword.value.trim()) {
    const keyword = searchKeyword.value.toLowerCase()
    data = data.filter(item =>
      item.bizDiv?.toLowerCase().includes(keyword) ||
      item.bizMgr?.toLowerCase().includes(keyword) ||
      item.dev?.toLowerCase().includes(keyword) ||
      item.oper?.toLowerCase().includes(keyword) ||
      item.status?.toLowerCase().includes(keyword) ||
      item.bitbucketName?.toLowerCase().includes(keyword) ||
      item.bambooName?.toLowerCase().includes(keyword) ||
      item.deployMgr?.toLowerCase().includes(keyword)
    )
  }

  // 상태 필터링
  if (selectedStatus.value !== 'all') {
    data = data.filter(item => item.status === selectedStatus.value)
  }

  return data
})

// 상태 클래스 반환
const getStatusClass = (status: string) => {
  const statusMap: Record<string, string> = {
    'active': 'status-active',
    'inactive': 'status-inactive',
    'pending': 'status-pending',
    'maintenance': 'status-maintenance'
  }
  return statusMap[status] || 'status-default'
}

// 상태 필터 설정
const setStatusFilter = (status: string) => {
  selectedStatus.value = status
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

// 모든 NULL 프로젝트관리 조회
const loadAllData = async () => {
  loading.value = true
  error.value = null
  searchKeyword.value = ''
  selectedStatus.value = 'all'

  try {
    console.log('🚀 Loading NULL ProjMgmt data...')
    const data = await ProjectService.getNullProjMgmtDtos()
    nullProjMgmtData.value = data
    handleSuccess(data, 'NULL 프로젝트관리 조회')
    console.log(`✅ Loaded ${data.length} NULL ProjMgmt records`)
  } catch (err) {
    handleError(err, 'NULL 프로젝트관리 조회')
    nullProjMgmtData.value = []
  }
}

// 검색 핸들러
const handleSearch = () => {
  // filteredData computed에서 자동으로 필터링됨
  console.log(`🔍 Searching with keyword: ${searchKeyword.value}`)
}

// 초기화 핸들러
const handleReset = () => {
  searchKeyword.value = ''
  selectedStatus.value = 'all'
  console.log('🔄 Filters reset')
}

// 재시도 핸들러
const retryLoad = () => {
  loadAllData()
}

// Excel 다운로드
const downloadExcel = async () => {
  if (nullProjMgmtData.value.length === 0) {
    error.value = '다운로드할 데이터가 없습니다.'
    return
  }

  loading.value = true
  try {
    console.log('📊 Starting Excel download...')
    const blob = await ProjectService.downloadNullProjMgmtExcel()

    // 파일 다운로드
    const url = window.URL.createObjectURL(blob)
    const link = document.createElement('a')
    link.href = url
    link.download = `null-projmgmt-${new Date().toISOString().slice(0, 19).replace(/[:]/g, '-')}.xlsx`
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
  loadAllData()
})
</script>

<style scoped>
.null-projmgmt {
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

.search-group, .filter-group {
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

/* 필터 버튼들 */
.filter-buttons {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.filter-btn {
  padding: 8px 16px;
  border: 2px solid #e1e8ed;
  background: white;
  border-radius: 6px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
}

.filter-btn.active {
  background: #e74c3c;
  border-color: #e74c3c;
  color: white;
}

.filter-btn:not(.active):hover {
  border-color: #e74c3c;
  color: #e74c3c;
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
  background: linear-gradient(90deg, #e74c3c, #c0392b, #e74c3c);
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

.error-title, .empty-title {
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
  background: #e74c3c;
  border-color: #e74c3c;
  color: white;
}

.view-btn:not(.active):hover {
  border-color: #e74c3c;
  color: #e74c3c;
}

/* 카드 뷰 */
.projmgmt-cards {
  padding: 24px;
  display: grid;
  gap: 24px;
}

.projmgmt-card {
  border: 1px solid #e1e8ed;
  border-radius: 12px;
  overflow: hidden;
  transition: box-shadow 0.2s ease, transform 0.2s ease;
}

.projmgmt-card:hover {
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
  transform: translateY(-2px);
}

.projmgmt-header {
  background: #f8f9fa;
  padding: 20px;
  border-bottom: 1px solid #e1e8ed;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}

.projmgmt-info {
  flex: 1;
}

.projmgmt-title {
  font-size: 18px;
  font-weight: 600;
  color: #2c3e50;
  margin: 0 0 12px 0;
  display: flex;
  align-items: center;
  gap: 12px;
  flex-wrap: wrap;
}

.projmgmt-id {
  background: #e74c3c;
  color: white;
  padding: 4px 12px;
  border-radius: 6px;
  font-size: 14px;
  font-weight: 700;
}

.projmgmt-status {
  padding: 4px 12px;
  border-radius: 6px;
  font-size: 12px;
  font-weight: 600;
  text-transform: uppercase;
}

.status-active {
  background: #27ae60;
  color: white;
}

.status-inactive {
  background: #95a5a6;
  color: white;
}

.status-pending {
  background: #f39c12;
  color: white;
}

.status-maintenance {
  background: #e67e22;
  color: white;
}

.status-default {
  background: #bdc3c7;
  color: white;
}

.projmgmt-basic-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.info-row {
  display: flex;
  gap: 8px;
}

.info-label {
  font-weight: 600;
  color: #7f8c8d;
  min-width: 80px;
}

.info-value {
  color: #2c3e50;
}

.projmgmt-meta {
  text-align: right;
  font-size: 12px;
  color: #95a5a6;
}

.meta-item {
  display: flex;
  flex-direction: column;
  gap: 2px;
  margin-bottom: 8px;
}

.meta-label {
  font-weight: 500;
  color: #7f8c8d;
}

.meta-value {
  color: #2c3e50;
}

/* 상세 정보 섹션 */
.projmgmt-details {
  padding: 20px;
}

.details-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 20px;
}

.detail-group {
  background: #f8f9fa;
  padding: 16px;
  border-radius: 8px;
  border-left: 4px solid #e74c3c;
}

.group-title {
  font-size: 16px;
  font-weight: 600;
  color: #2c3e50;
  margin: 0 0 12px 0;
}

.detail-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
  padding: 4px 0;
}

.detail-item:last-child {
  margin-bottom: 0;
}

.detail-label {
  font-weight: 600;
  color: #7f8c8d;
  font-size: 14px;
}

.detail-value {
  color: #2c3e50;
  font-size: 14px;
  text-align: right;
  max-width: 150px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.bamboo-key-null {
  color: #e74c3c;
  font-weight: 600;
  font-style: italic;
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

.status-badge {
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 11px;
  font-weight: 600;
  text-transform: uppercase;
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
  .null-projmgmt {
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

  .filter-buttons {
    justify-content: center;
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
    justify-content: center;
  }

  .projmgmt-header {
    flex-direction: column;
    gap: 12px;
  }

  .projmgmt-meta {
    text-align: left;
  }

  .projmgmt-title {
    flex-direction: column;
    align-items: flex-start;
    gap: 8px;
  }

  .details-grid {
    grid-template-columns: 1fr;
  }

  .detail-item {
    flex-direction: column;
    align-items: flex-start;
    gap: 4px;
  }

  .detail-value {
    text-align: left;
    max-width: none;
  }

  .loading-bar {
    width: 250px;
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
    text-align: center;
  }

  .stat-value {
    font-size: 20px;
  }

  .detail-group {
    padding: 12px;
  }

  .group-title {
    font-size: 14px;
  }

  .detail-item {
    font-size: 13px;
  }
}
</style>
