<template>
  <div class="dashboard">
    <!-- 페이지 헤더 -->
    <div class="page-header">
      <h1 class="page-title">대시보드</h1>
      <p class="page-description">
        SCM 프로젝트와 빌드 현황을 한눈에 확인하세요
      </p>
    </div>

    <!-- 통계 카드 -->
    <div class="stats-grid">
      <div class="stat-card" v-for="stat in stats" :key="stat.title">
        <div class="stat-icon">{{ stat.icon }}</div>
        <div class="stat-content">
          <h3 class="stat-title">{{ stat.title }}</h3>
          <div class="stat-value">{{ stat.value }}</div>
          <div class="stat-trend" :class="stat.trend">
            <span class="trend-icon">{{ stat.trendIcon }}</span>
            <span class="trend-text">{{ stat.trendText }}</span>
          </div>
        </div>
      </div>
    </div>

    <!-- 빠른 액션 -->
    <div class="quick-actions">
      <h2 class="section-title">빠른 액션</h2>
      <div class="actions-grid">
        <button
          v-for="action in quickActions"
          :key="action.title"
          class="action-card"
          @click="handleQuickAction(action.action)"
        >
          <div class="action-icon">{{ action.icon }}</div>
          <div class="action-content">
            <h3 class="action-title">{{ action.title }}</h3>
            <p class="action-description">{{ action.description }}</p>
          </div>
        </button>
      </div>
    </div>

    <!-- 최근 활동 -->
    <div class="recent-activity">
      <h2 class="section-title">최근 활동</h2>
      <div class="activity-list">
        <div
          v-for="activity in recentActivities"
          :key="activity.id"
          class="activity-item"
        >
          <div class="activity-icon">{{ activity.icon }}</div>
          <div class="activity-content">
            <h4 class="activity-title">{{ activity.title }}</h4>
            <p class="activity-description">{{ activity.description }}</p>
            <div class="activity-meta">
              <span class="activity-time">{{ activity.time }}</span>
              <span class="activity-status" :class="activity.status">
                {{ activity.statusText }}
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// 통계 데이터
const stats = ref([
  {
    title: '총 프로젝트',
    value: '24',
    icon: '',
    trend: 'positive',
    trendIcon: '',
    trendText: '+3 이번 주'
  },
  {
    title: '활성 빌드',
    value: '15',
    icon: '',
    trend: 'positive',
    trendIcon: '',
    trendText: '정상 동작'
  },
  {
    title: '완료된 배포',
    value: '8',
    icon: '',
    trend: 'neutral',
    trendIcon: '',
    trendText: '오늘'
  },
  {
    title: '대기중 작업',
    value: '3',
    icon: '',
    trend: 'negative',
    trendIcon: '',
    trendText: '주의 필요'
  }
])

// 빠른 액션
const quickActions = ref([
  {
    title: '프로젝트 조회',
    description: '전체 프로젝트 목록을 확인합니다',
    icon: '',
    action: 'viewProjects'
  },
  {
    title: '빌드 관리',
    description: '빌드 상태를 확인하고 관리합니다',
    icon: '',
    action: 'manageBUILDS'
  },
  {
    title: 'API 테스트',
    description: 'API 연결 상태를 테스트합니다',
    icon: '',
    action: 'testApi'
  },
  {
    title: '데이터 내보내기',
    description: 'Excel 파일로 데이터를 다운로드합니다',
    icon: '',
    action: 'exportData'
  }
])

// 최근 활동
const recentActivities = ref([
  {
    id: 1,
    title: 'LCTC 프로젝트 빌드 완료',
    description: 'LCTC-WEB-ADMIN 빌드가 성공적으로 완료되었습니다',
    icon: '✅',
    time: '2분 전',
    status: 'success',
    statusText: '성공'
  },
  {
    id: 2,
    title: '새 프로젝트 등록',
    description: 'SCM-DASHBOARD 프로젝트가 시스템에 등록되었습니다',
    icon: '',
    time: '15분 전',
    status: 'info',
    statusText: '등록'
  },
  {
    id: 3,
    title: '빌드 실패 알림',
    description: 'TEST-PROJECT 빌드에서 오류가 발생했습니다',
    icon: '',
    time: '1시간 전',
    status: 'error',
    statusText: '실패'
  },
  {
    id: 4,
    title: 'API 연결 재시도',
    description: 'API 서버 연결이 복구되어 정상 작동 중입니다',
    icon: '',
    time: '2시간 전',
    status: 'warning',
    statusText: '복구'
  }
])

// 빠른 액션 핸들러
const handleQuickAction = (action: string) => {
  switch (action) {
    case 'viewProjects':
      router.push('/projects')  // 다음 단계에서 활성화
      //alert('프로젝트 관리 화면은 다음 단계에서 구현됩니다')
      break
    case 'manageBUILDS':
      router.push('/project-builds')
      //alert('빌드 관리 화면은 다음 단계에서 구현됩니다')
      break
    case 'testApi':
      router.push('/api-test')
      break
    case 'exportData':
      // Excel 다운로드 로직 (나중에 구현)
      alert('Excel 다운로드 기능은 다음 단계에서 구현됩니다')
      break
  }
}

// 컴포넌트 마운트 시 데이터 로드
onMounted(() => {
  // 실제 데이터 로드 로직은 나중에 구현
  console.log('Dashboard 초기화 완료')
})
</script>

<style scoped>
.dashboard {
  max-width: 1200px;
  margin: 0 auto;
}

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

.section-title {
  font-size: 20px;
  font-weight: 600;
  color: #2c3e50;
  margin: 0 0 16px 0;
}

/* 통계 카드 */
.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
  margin-bottom: 40px;
}

.stat-card {
  background: white;
  padding: 24px;
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
  font-size: 40px;
  width: 64px;
  height: 64px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.stat-content {
  flex: 1;
}

.stat-title {
  font-size: 14px;
  color: #7f8c8d;
  margin: 0 0 4px 0;
  font-weight: 500;
}

.stat-value {
  font-size: 28px;
  font-weight: 700;
  color: #2c3e50;
  margin: 0 0 8px 0;
}

.stat-trend {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 12px;
}

.stat-trend.positive {
  color: #27ae60;
}

.stat-trend.negative {
  color: #e74c3c;
}

.stat-trend.neutral {
  color: #f39c12;
}

.trend-icon {
  font-size: 12px;
}

/* 빠른 액션 */
.quick-actions {
  margin-bottom: 40px;
}

.actions-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 16px;
}

.action-card {
  background: white;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  display: flex;
  align-items: flex-start;
  gap: 16px;
  border: 2px solid transparent;
  cursor: pointer;
  transition: all 0.2s ease;
  text-align: left;
}

.action-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
  border-color: #3498db;
}

.action-icon {
  font-size: 32px;
  width: 48px;
  height: 48px;
  background: #2c3e50; /* 그라디언트 제거하고 단색으로 */
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
  color: white;
}

.action-content {
  flex: 1;
}

.action-title {
  font-size: 16px;
  font-weight: 600;
  color: #2c3e50;
  margin: 0 0 8px 0;
}

.action-description {
  font-size: 14px;
  color: #7f8c8d;
  margin: 0;
  line-height: 1.4;
}

/* 최근 활동 */
.recent-activity {
  margin-bottom: 40px;
}

.activity-list {
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  overflow: hidden;
}

.activity-item {
  display: flex;
  align-items: flex-start;
  gap: 16px;
  padding: 20px;
  border-bottom: 1px solid #ecf0f1;
  transition: background-color 0.2s ease;
}

.activity-item:last-child {
  border-bottom: none;
}

.activity-item:hover {
  background-color: #f8f9fa;
}

.activity-icon {
  font-size: 24px;
  width: 40px;
  height: 40px;
  background: #ecf0f1;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.activity-content {
  flex: 1;
}

.activity-title {
  font-size: 16px;
  font-weight: 500;
  color: #495057;
  margin: 0 0 4px 0;
}

.activity-description {
  font-size: 14px;
  color: #6c757d;
  margin: 0 0 12px 0;
  line-height: 1.5;
}.activity-title {
   font-size: 18px;
   font-weight: 600;
   color: var(--text-primary);
   margin: 0 0 8px 0;
 }

.activity-description {
  font-size: 15px;
  color: var(--text-secondary);
  margin: 0 0 16px 0;
  line-height: 1.6;
}

.activity-meta {
  display: flex;
  align-items: center;
  gap: 20px;
  font-size: 13px;
}

.activity-time {
  color: var(--text-muted);
  font-weight: 500;
}

.activity-status {
  padding: 6px 16px;
  border-radius: 20px;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.8px;
  font-size: 11px;
  border: 2px solid transparent;
}

.activity-status.success {
  background: linear-gradient(135deg, var(--success-color), #40c057);
  color: white;
  box-shadow: 0 4px 12px rgba(81, 207, 102, 0.3);
}

.activity-status.error {
  background: linear-gradient(135deg, var(--error-color), var(--primary-dark));
  color: white;
  box-shadow: 0 4px 12px rgba(255, 107, 107, 0.3);
}

.activity-status.warning {
  background: linear-gradient(135deg, var(--warning-color), #fcc419);
  color: var(--dark-bg);
  box-shadow: 0 4px 12px rgba(255, 212, 59, 0.3);
}

.activity-status.info {
  background: linear-gradient(135deg, var(--info-color), var(--secondary-color));
  color: white;
  box-shadow: 0 4px 12px rgba(116, 192, 252, 0.3);
}

/* 반응형 디자인 */
@media (max-width: 768px) {
  .stats-grid {
    grid-template-columns: 1fr;
    gap: 20px;
  }

  .actions-grid {
    grid-template-columns: 1fr;
    gap: 16px;
  }

  .stat-card {
    padding: 24px;
    gap: 20px;
  }

  .stat-icon {
    width: 72px;
    height: 72px;
    font-size: 42px;
  }

  .action-card {
    padding: 24px;
    gap: 20px;
  }

  .action-icon {
    width: 60px;
    height: 60px;
    font-size: 36px;
  }

  .activity-item {
    padding: 24px;
    gap: 20px;
  }

  .activity-icon {
    width: 48px;
    height: 48px;
    font-size: 24px;
  }

  .page-title {
    font-size: 32px;
  }

  .section-title {
    font-size: 22px;
  }

  .content-wrapper {
    padding: 20px;
  }
}
</style>
