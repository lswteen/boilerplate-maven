<template>
  <div class="api-test">
    <h2>API 연동 테스트</h2>

    <!-- API 상태 표시 -->
    <div class="status-section">
      <h3>API 서버 상태</h3>
      <button @click="checkApiStatus" :disabled="loading">
        {{ loading ? '확인 중...' : 'API 상태 확인' }}
      </button>
      <p class="status" :class="apiStatus ? 'success' : 'error'">
        상태: {{ apiStatus ? '✅ 연결됨' : '❌ 연결 실패' }}
      </p>
    </div>

    <!-- Project-ProjMgmt 테스트 -->
    <div class="test-section">
      <h3>🏗️ Project-ProjMgmt API 테스트</h3>

      <div class="button-group">
        <button @click="testProjectProjMgmt" :disabled="loading">
          매핑된 프로젝트 전체 조회
        </button>
        <button @click="testUnmappedProjects" :disabled="loading">
          매핑되지 않은 프로젝트 조회
        </button>
      </div>

      <div class="search-group">
        <input
          v-model="searchProjectKey"
          placeholder="프로젝트 키 입력 (예: LCTC)"
          class="search-input"
          @keyup.enter="testProjectByKey"
        />
        <button @click="testProjectByKey" :disabled="loading || !searchProjectKey.trim()">
          프로젝트 키로 조회
        </button>
      </div>

      <div class="search-group">
        <input
          v-model="searchBambooKey"
          placeholder="Bamboo 키 입력"
          class="search-input"
          @keyup.enter="testProjectByBambooKey"
        />
        <button @click="testProjectByBambooKey" :disabled="loading || !searchBambooKey.trim()">
          Bamboo 키로 조회
        </button>
      </div>

      <p v-if="projectProjMgmtData.length > 0" class="success">
        ✅ 성공: {{ projectProjMgmtData.length }}개 프로젝트 조회됨
      </p>
      <div v-if="projectProjMgmtData.length > 0" class="data-preview">
        <h4>첫 번째 프로젝트 정보:</h4>
        <pre>{{ JSON.stringify(projectProjMgmtData[0], null, 2) }}</pre>
      </div>
    </div>

    <!-- Project-Build 테스트 -->
    <div class="test-section">
      <h3>Project-Build API 테스트</h3>

      <div class="button-group">
        <button @click="testProjectBuild" :disabled="loading">
          프로젝트-빌드 전체 조회
        </button>
        <button @click="testProjectBuildSearch" :disabled="loading">
          검색 파라미터로 조회
        </button>
      </div>

      <div class="search-group">
        <input
          v-model="searchBuildProjectKey"
          placeholder="프로젝트 키 입력"
          class="search-input"
          @keyup.enter="testProjectBuildByKey"
        />
        <button @click="testProjectBuildByKey" :disabled="loading || !searchBuildProjectKey.trim()">
          프로젝트 키로 빌드 조회
        </button>
      </div>

      <div class="search-group">
        <input
          v-model="searchProjectId"
          placeholder="프로젝트 ID 입력"
          class="search-input"
          type="number"
          @keyup.enter="testProjectBuildById"
        />
        <button @click="testProjectBuildById" :disabled="loading || !searchProjectId">
          프로젝트 ID로 빌드 조회
        </button>
      </div>

      <p v-if="projectBuildData.length > 0" class="success">
        ✅ 성공: {{ projectBuildData.length }}개 프로젝트 조회됨
      </p>
      <div v-if="projectBuildData.length > 0" class="data-preview">
        <h4>첫 번째 프로젝트 정보:</h4>
        <pre>{{ JSON.stringify(projectBuildData[0], null, 2) }}</pre>
      </div>
    </div>

    <!-- NULL ProjMgmt 테스트 -->
    <div class="test-section">
      <h3>NULL ProjMgmt API 테스트</h3>

      <div class="button-group">
        <button @click="testNullProjMgmtDto" :disabled="loading">
          NULL 프로젝트관리 조회 (DTO - 권장)
        </button>
        <button @click="testNullProjMgmtEntity" :disabled="loading">
          NULL 프로젝트관리 조회 (Entity - N+1 발생)
        </button>
      </div>

      <p v-if="nullProjMgmtData.length > 0" class="success">
        ✅ 성공: {{ nullProjMgmtData.length }}개 NULL 프로젝트관리 조회됨
      </p>
      <div v-if="nullProjMgmtData.length > 0" class="data-preview">
        <h4>첫 번째 NULL ProjMgmt 정보:</h4>
        <pre>{{ JSON.stringify(nullProjMgmtData[0], null, 2) }}</pre>
      </div>
    </div>

    <!-- Excel 다운로드 테스트 -->
    <div class="test-section">
      <h3>Excel 다운로드 API 테스트</h3>

      <div class="button-group">
        <button @click="testDownloadProjectProjMgmtExcel" :disabled="loading">
          프로젝트-프로젝트관리 Excel 다운로드
        </button>
        <button @click="testDownloadProjectBuildsExcel" :disabled="loading">
          프로젝트-빌드 Excel 다운로드
        </button>
        <button @click="testDownloadNullProjMgmtExcel" :disabled="loading">
          NULL 프로젝트관리 Excel 다운로드
        </button>
        <button @click="testDownloadUnmappedProjectsExcel" :disabled="loading">
          매핑되지 않은 프로젝트 Excel 다운로드
        </button>
      </div>

      <p v-if="downloadStatus" class="success">
        {{ downloadStatus }}
      </p>
    </div>

    <!-- 에러 표시 -->
    <div v-if="error" class="error-section">
      <h3>❌ 에러 발생</h3>
      <p class="error">{{ error }}</p>
      <button @click="clearError" class="clear-btn">에러 메시지 지우기</button>
    </div>

    <!-- 로딩 표시 -->
    <div v-if="loading" class="loading-overlay">
      <div class="loading-spinner">⏳</div>
      <p>처리 중입니다...</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ProjectService } from '@/services/projectService'
import { checkApiHealth } from '@/services/api'
import type {
  ProjectProjMgmtResponse,
  ProjectBuildResponse,
  ProjMgmtDto,
  ProjectResponse
} from '@/types/api'

// 반응형 상태
const loading = ref(false)
const apiStatus = ref(false)
const error = ref('')
const downloadStatus = ref('')

// 데이터 상태
const projectProjMgmtData = ref<(ProjectProjMgmtResponse | ProjectResponse)[]>([])
const projectBuildData = ref<ProjectBuildResponse[]>([])
const nullProjMgmtData = ref<ProjMgmtDto[]>([])

// 검색 상태
const searchProjectKey = ref('')
const searchBambooKey = ref('')
const searchBuildProjectKey = ref('')
const searchProjectId = ref<number | null>(null)

// 공통 에러 처리
const handleError = (err: any, context: string) => {
  console.error(`❌ ${context} 실패:`, err)
  error.value = `${context} 실패: ${err.response?.data?.message || err.message}`
  loading.value = false
}

// 공통 성공 처리
const handleSuccess = (data: any, context: string) => {
  console.log(`✅ ${context} 성공:`, data)
  error.value = ''
  loading.value = false
}

// API 상태 확인
const checkApiStatus = async () => {
  loading.value = true
  error.value = ''

  try {
    apiStatus.value = await checkApiHealth()
    handleSuccess(apiStatus.value, 'API 상태 확인')
  } catch (err) {
    apiStatus.value = false
    handleError(err, 'API 상태 확인')
  }
}

// 🎯 Project-ProjMgmt API 테스트들
const testProjectProjMgmt = async () => {
  loading.value = true
  try {
    projectProjMgmtData.value = await ProjectService.getAllProjectsWithProjMgmt()
    handleSuccess(projectProjMgmtData.value, 'Project-ProjMgmt 전체 조회')
  } catch (err) {
    handleError(err, 'Project-ProjMgmt 전체 조회')
  }
}

const testUnmappedProjects = async () => {
  loading.value = true
  try {
    const data = await ProjectService.getUnmappedProjects()
    projectProjMgmtData.value = data
    handleSuccess(data, '매핑되지 않은 프로젝트 조회')
  } catch (err) {
    handleError(err, '매핑되지 않은 프로젝트 조회')
  }
}

const testProjectByKey = async () => {
  if (!searchProjectKey.value.trim()) return

  loading.value = true
  try {
    const data = await ProjectService.getProjectWithProjMgmtByKey(searchProjectKey.value.trim())
    projectProjMgmtData.value = [data]
    handleSuccess(data, `프로젝트 키(${searchProjectKey.value}) 조회`)
  } catch (err) {
    handleError(err, `프로젝트 키(${searchProjectKey.value}) 조회`)
  }
}

const testProjectByBambooKey = async () => {
  if (!searchBambooKey.value.trim()) return

  loading.value = true
  try {
    const data = await ProjectService.getProjectWithProjMgmtByBambooKey(searchBambooKey.value.trim())
    projectProjMgmtData.value = [data]
    handleSuccess(data, `Bamboo 키(${searchBambooKey.value}) 조회`)
  } catch (err) {
    handleError(err, `Bamboo 키(${searchBambooKey.value}) 조회`)
  }
}

// 🎯 Project-Build API 테스트들
const testProjectBuild = async () => {
  loading.value = true
  try {
    projectBuildData.value = await ProjectService.getAllProjectsWithBuilds()
    handleSuccess(projectBuildData.value, 'Project-Build 전체 조회')
  } catch (err) {
    handleError(err, 'Project-Build 전체 조회')
  }
}

const testProjectBuildSearch = async () => {
  loading.value = true
  try {
    projectBuildData.value = await ProjectService.searchProjectsWithBuilds()
    handleSuccess(projectBuildData.value, 'Project-Build 검색 조회')
  } catch (err) {
    handleError(err, 'Project-Build 검색 조회')
  }
}

const testProjectBuildByKey = async () => {
  if (!searchBuildProjectKey.value.trim()) return

  loading.value = true
  try {
    const data = await ProjectService.getProjectWithBuildsByKey(searchBuildProjectKey.value.trim())
    projectBuildData.value = [data]
    handleSuccess(data, `프로젝트 키(${searchBuildProjectKey.value}) 빌드 조회`)
  } catch (err) {
    handleError(err, `프로젝트 키(${searchBuildProjectKey.value}) 빌드 조회`)
  }
}

const testProjectBuildById = async () => {
  if (!searchProjectId.value) return

  loading.value = true
  try {
    const data = await ProjectService.getProjectWithBuildsById(searchProjectId.value)
    projectBuildData.value = [data]
    handleSuccess(data, `프로젝트 ID(${searchProjectId.value}) 빌드 조회`)
  } catch (err) {
    handleError(err, `프로젝트 ID(${searchProjectId.value}) 빌드 조회`)
  }
}

// 🎯 NULL ProjMgmt API 테스트들
const testNullProjMgmtDto = async () => {
  loading.value = true
  try {
    nullProjMgmtData.value = await ProjectService.getNullProjMgmtDtos()
    handleSuccess(nullProjMgmtData.value, 'NULL ProjMgmt DTO 조회')
  } catch (err) {
    handleError(err, 'NULL ProjMgmt DTO 조회')
  }
}

const testNullProjMgmtEntity = async () => {
  loading.value = true
  try {
    nullProjMgmtData.value = await ProjectService.getNullProjMgmtEntities()
    handleSuccess(nullProjMgmtData.value, 'NULL ProjMgmt Entity 조회')
  } catch (err) {
    handleError(err, 'NULL ProjMgmt Entity 조회')
  }
}

// 🎯 Excel 다운로드 테스트들
const downloadFile = (blob: Blob, filename: string) => {
  const url = window.URL.createObjectURL(blob)
  const link = document.createElement('a')
  link.href = url
  link.download = filename
  document.body.appendChild(link)
  link.click()
  document.body.removeChild(link)
  window.URL.revokeObjectURL(url)
}

const testDownloadProjectProjMgmtExcel = async () => {
  loading.value = true
  try {
    const blob = await ProjectService.downloadProjectProjMgmtExcel()
    downloadFile(blob, 'project-projmgmt.xlsx')
    downloadStatus.value = '✅ 프로젝트-프로젝트관리 Excel 다운로드 완료'
    handleSuccess(blob, 'Project-ProjMgmt Excel 다운로드')
  } catch (err) {
    handleError(err, 'Project-ProjMgmt Excel 다운로드')
  }
}

const testDownloadProjectBuildsExcel = async () => {
  loading.value = true
  try {
    const blob = await ProjectService.downloadProjectBuildsExcel()
    downloadFile(blob, 'project-builds.xlsx')
    downloadStatus.value = '✅ 프로젝트-빌드 Excel 다운로드 완료'
    handleSuccess(blob, 'Project-Build Excel 다운로드')
  } catch (err) {
    handleError(err, 'Project-Build Excel 다운로드')
  }
}

const testDownloadNullProjMgmtExcel = async () => {
  loading.value = true
  try {
    const blob = await ProjectService.downloadNullProjMgmtExcel()
    downloadFile(blob, 'null-projmgmt.xlsx')
    downloadStatus.value = '✅ NULL 프로젝트관리 Excel 다운로드 완료'
    handleSuccess(blob, 'NULL ProjMgmt Excel 다운로드')
  } catch (err) {
    handleError(err, 'NULL ProjMgmt Excel 다운로드')
  }
}

const testDownloadUnmappedProjectsExcel = async () => {
  loading.value = true
  try {
    const blob = await ProjectService.downloadUnmappedProjectsExcel()
    downloadFile(blob, 'unmapped-projects.xlsx')
    downloadStatus.value = '✅ 매핑되지 않은 프로젝트 Excel 다운로드 완료'
    handleSuccess(blob, '매핑되지 않은 프로젝트 Excel 다운로드')
  } catch (err) {
    handleError(err, '매핑되지 않은 프로젝트 Excel 다운로드')
  }
}

// 에러 클리어
const clearError = () => {
  error.value = ''
  downloadStatus.value = ''
}

// 컴포넌트 마운트 시 API 상태 확인
onMounted(() => {
  checkApiStatus()
})
</script>

<style scoped>
.api-test {
  padding: 20px;
  max-width: 1000px;
  margin: 0 auto;
  position: relative;
}

.status-section, .test-section, .error-section {
  margin: 20px 0;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 12px;
  background: white;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.status-section {
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  border-color: #dee2e6;
}

.test-section {
  background: #ffffff;
}

.error-section {
  background: linear-gradient(135deg, #fff5f5 0%, #fed7d7 100%);
  border-color: #fc8181;
}

/* 버튼 그룹 */
.button-group {
  display: flex;
  gap: 12px;
  flex-wrap: wrap;
  margin-bottom: 16px;
}

.search-group {
  display: flex;
  gap: 12px;
  align-items: center;
  margin-bottom: 16px;
  flex-wrap: wrap;
}

.search-input {
  flex: 1;
  min-width: 200px;
  padding: 10px 15px;
  border: 2px solid #e2e8f0;
  border-radius: 8px;
  font-size: 14px;
  transition: border-color 0.2s ease;
}

.search-input:focus {
  outline: none;
  border-color: #3182ce;
  box-shadow: 0 0 0 3px rgba(49, 130, 206, 0.1);
}

/* 버튼 스타일 */
button {
  padding: 12px 18px;
  margin: 4px 0;
  border: none;
  border-radius: 8px;
  background: linear-gradient(135deg, #4299e1 0%, #3182ce 100%);
  color: white;
  cursor: pointer;
  font-size: 14px;
  font-weight: 600;
  transition: all 0.2s ease;
  white-space: nowrap;
}

button:hover:not(:disabled) {
  background: linear-gradient(135deg, #3182ce 0%, #2c5282 100%);
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(49, 130, 206, 0.4);
}

button:disabled {
  background: #a0aec0;
  cursor: not-allowed;
  transform: none;
  box-shadow: none;
}

.clear-btn {
  background: linear-gradient(135deg, #f56565 0%, #e53e3e 100%);
}

.clear-btn:hover:not(:disabled) {
  background: linear-gradient(135deg, #e53e3e 0%, #c53030 100%);
}

/* 상태 메시지 */
.status.success, .success {
  color: #38a169;
  font-weight: bold;
  margin: 12px 0;
  padding: 8px 12px;
  background: rgba(56, 161, 105, 0.1);
  border-radius: 6px;
  border-left: 4px solid #38a169;
}

.status.error, .error {
  color: #e53e3e;
  font-weight: bold;
  margin: 12px 0;
  padding: 8px 12px;
  background: rgba(229, 62, 62, 0.1);
  border-radius: 6px;
  border-left: 4px solid #e53e3e;
}

/* 데이터 미리보기 */
.data-preview {
  margin-top: 16px;
  padding: 16px;
  background: #f7fafc;
  border-radius: 8px;
  border: 1px solid #e2e8f0;
  max-height: 400px;
  overflow-y: auto;
}

.data-preview h4 {
  margin: 0 0 12px 0;
  color: #2d3748;
  font-size: 16px;
  font-weight: 600;
}

pre {
  font-size: 12px;
  white-space: pre-wrap;
  word-break: break-all;
  margin: 0;
  color: #4a5568;
  line-height: 1.5;
}

/* 제목 스타일 */
h2 {
  color: #2d3748;
  border-bottom: 3px solid #4299e1;
  padding-bottom: 12px;
  margin-bottom: 24px;
  font-size: 28px;
  font-weight: 700;
}

h3 {
  color: #2d3748;
  margin: 0 0 16px 0;
  font-size: 20px;
  font-weight: 600;
  display: flex;
  align-items: center;
  gap: 8px;
}

/* 로딩 오버레이 */
.loading-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  z-index: 1000;
  color: white;
}

.loading-spinner {
  font-size: 48px;
  animation: spin 1s linear infinite;
  margin-bottom: 16px;
}

@keyframes spin {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}

/* 반응형 디자인 */
@media (max-width: 768px) {
  .api-test {
    padding: 12px;
  }

  .button-group, .search-group {
    flex-direction: column;
  }

  .search-input {
    min-width: auto;
    width: 100%;
  }

  button {
    width: 100%;
  }

  h2 {
    font-size: 24px;
  }

  h3 {
    font-size: 18px;
  }
}
</style>
