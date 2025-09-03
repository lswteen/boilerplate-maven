<template>
  <div class="api-test">
    <h2>🔧 API 연동 테스트</h2>

    <!-- API 상태 표시 -->
    <div class="status-section">
      <h3>📡 API 서버 상태</h3>
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
      <button @click="testProjectProjMgmt" :disabled="loading">
        매핑된 프로젝트 조회
      </button>
      <p v-if="projectProjMgmtData.length > 0" class="success">
        ✅ 성공: {{ projectProjMgmtData.length }}개 프로젝트 조회됨
      </p>
      <div v-if="projectProjMgmtData.length > 0" class="data-preview">
        <h4>📋 첫 번째 프로젝트 정보:</h4>
        <pre>{{ JSON.stringify(projectProjMgmtData[0], null, 2) }}</pre>
      </div>
    </div>

    <!-- Project-Build 테스트 -->
    <div class="test-section">
      <h3>🔨 Project-Build API 테스트</h3>
      <button @click="testProjectBuild" :disabled="loading">
        프로젝트-빌드 조회
      </button>
      <p v-if="projectBuildData.length > 0" class="success">
        ✅ 성공: {{ projectBuildData.length }}개 프로젝트 조회됨
      </p>
      <div v-if="projectBuildData.length > 0" class="data-preview">
        <h4>📋 첫 번째 프로젝트 정보:</h4>
        <pre>{{ JSON.stringify(projectBuildData[0], null, 2) }}</pre>
      </div>
    </div>

    <!-- NULL ProjMgmt 테스트 -->
    <div class="test-section">
      <h3>⚠️ NULL ProjMgmt API 테스트</h3>
      <button @click="testNullProjMgmt" :disabled="loading">
        NULL 프로젝트관리 조회
      </button>
      <p v-if="nullProjMgmtData.length > 0" class="success">
        ✅ 성공: {{ nullProjMgmtData.length }}개 NULL 프로젝트관리 조회됨
      </p>
    </div>

    <!-- 에러 표시 -->
    <div v-if="error" class="error-section">
      <h3>❌ 에러 발생</h3>
      <p class="error">{{ error }}</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import ProjectService from '@/services/projectService'
import { checkApiHealth } from '@/services/api'
import type { ProjectProjMgmtResponse, ProjectBuildResponse, ProjMgmtDto } from '@/types/api'

// 반응형 상태
const loading = ref(false)
const apiStatus = ref(false)
const error = ref('')
const projectProjMgmtData = ref<ProjectProjMgmtResponse[]>([])
const projectBuildData = ref<ProjectBuildResponse[]>([])
const nullProjMgmtData = ref<ProjMgmtDto[]>([])

// API 상태 확인
const checkApiStatus = async () => {
  loading.value = true
  error.value = ''

  try {
    apiStatus.value = await checkApiHealth()
  } catch (err) {
    console.error('API 상태 확인 실패:', err)
    apiStatus.value = false
    error.value = 'API 서버에 연결할 수 없습니다'
  } finally {
    loading.value = false
  }
}

// Project-ProjMgmt 테스트
const testProjectProjMgmt = async () => {
  loading.value = true
  error.value = ''

  try {
    projectProjMgmtData.value = await ProjectService.getAllProjectsWithProjMgmt()
    console.log('✅ Project-ProjMgmt 데이터:', projectProjMgmtData.value)
  } catch (err: any) {
    console.error('❌ Project-ProjMgmt 조회 실패:', err)
    error.value = `Project-ProjMgmt 조회 실패: ${err.message}`
  } finally {
    loading.value = false
  }
}

// Project-Build 테스트
const testProjectBuild = async () => {
  loading.value = true
  error.value = ''

  try {
    projectBuildData.value = await ProjectService.getAllProjectsWithBuilds()
    console.log('✅ Project-Build 데이터:', projectBuildData.value)
  } catch (err: any) {
    console.error('❌ Project-Build 조회 실패:', err)
    error.value = `Project-Build 조회 실패: ${err.message}`
  } finally {
    loading.value = false
  }
}

// NULL ProjMgmt 테스트
const testNullProjMgmt = async () => {
  loading.value = true
  error.value = ''

  try {
    nullProjMgmtData.value = await ProjectService.getNullProjMgmtDtos()
    console.log('✅ NULL ProjMgmt 데이터:', nullProjMgmtData.value)
  } catch (err: any) {
    console.error('❌ NULL ProjMgmt 조회 실패:', err)
    error.value = `NULL ProjMgmt 조회 실패: ${err.message}`
  } finally {
    loading.value = false
  }
}

// 컴포넌트 마운트 시 API 상태 확인
onMounted(() => {
  checkApiStatus()
})
</script>

<style scoped>
.api-test {
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
}

.status-section, .test-section, .error-section {
  margin: 20px 0;
  padding: 15px;
  border: 1px solid #ddd;
  border-radius: 8px;
}

.status-section {
  background-color: #f8f9fa;
}

.test-section {
  background-color: #ffffff;
}

.error-section {
  background-color: #fff5f5;
  border-color: #fed7d7;
}

button {
  padding: 10px 15px;
  margin: 5px 0;
  border: none;
  border-radius: 5px;
  background-color: #007bff;
  color: white;
  cursor: pointer;
  font-size: 14px;
}

button:hover {
  background-color: #0056b3;
}

button:disabled {
  background-color: #6c757d;
  cursor: not-allowed;
}

.status.success {
  color: #28a745;
  font-weight: bold;
}

.status.error, .error {
  color: #dc3545;
  font-weight: bold;
}

.success {
  color: #28a745;
  font-weight: bold;
}

.data-preview {
  margin-top: 10px;
  padding: 10px;
  background-color: #f8f9fa;
  border-radius: 5px;
  max-height: 300px;
  overflow-y: auto;
}

pre {
  font-size: 12px;
  white-space: pre-wrap;
  word-break: break-all;
}

h2 {
  color: #333;
  border-bottom: 2px solid #007bff;
  padding-bottom: 10px;
}

h3 {
  color: #495057;
  margin-bottom: 10px;
}
</style>
