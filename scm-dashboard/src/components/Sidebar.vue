<template>
  <aside class="sidebar" :class="{ collapsed: isCollapsed }">
    <!-- 토글 버튼 -->
    <button class="toggle-btn" @click="handleToggle">
<!--      <span class="toggle-icon">{{ isCollapsed ? '▶' : '◀' }}</span>-->
    </button>

    <!-- 네비게이션 -->
    <nav class="sidebar-nav">
      <!-- 메인 메뉴 -->
      <ul class="nav-list">
        <li v-for="item in navigationItems" :key="item.name" class="nav-item">
          <router-link
            :to="item.path"
            class="nav-link"
            :class="{ 'active': $route.name === item.name }"
          >
            <span class="nav-icon">{{ item.icon }}</span>
            <span class="nav-label" v-if="!isCollapsed">{{ item.label }}</span>
            <span class="nav-badge" v-if="item.badge && !isCollapsed">{{ item.badge }}</span>
          </router-link>
        </li>
      </ul>

      <!-- 구분선 -->
      <div class="nav-divider" v-if="!isCollapsed"></div>

      <!-- 관리 메뉴 섹션
      <div class="nav-section" v-if="!isCollapsed">
        <div class="nav-section-title">관리</div>
        <ul class="nav-list">
          <li v-for="item in managementItems" :key="item.name" class="nav-item">
            <router-link
              :to="item.path"
              class="nav-link"
              :class="{ 'active': $route.name === item.name }"
            >
              <span class="nav-icon">{{ item.icon }}</span>
              <span class="nav-label">{{ item.label }}</span>
              <span class="nav-badge" v-if="item.badge">{{ item.badge }}</span>
            </router-link>
          </li>
        </ul>
      </div>
      -->
    </nav>
  </aside>
</template>

<script setup lang="ts">
interface NavigationItem {
  name: string
  path: string
  label: string
  icon: string
  badge?: string | number
}

const props = defineProps<{
  isCollapsed: boolean
}>()

const emit = defineEmits<{
  toggle: []
}>()

// 메인 네비게이션 메뉴 (프로젝트 관리 활성화)
const navigationItems: NavigationItem[] = [
  {
    name: 'Dashboard',
    path: '/',
    label: '대시보드',
    icon: '-'
  },
  {
    name: 'Projects',
    path: '/projects',
    label: '프로젝트 관리',
    icon: '-'
  },
  {
    name: 'ApiTest',
    path: '/api-test',
    label: 'API 테스트',
    icon: '-'
  }
  // 다음 단계에서 추가될 메뉴들
  // {
  //   name: 'Builds',
  //   path: '/builds',
  //   label: '빌드 관리',
  //   icon: '🔨'
  // }
]

// 관리 메뉴 (다음 단계에서 활성화)
const managementItems: NavigationItem[] = [
  // 다음 단계에서 활성화
  // {
  //   name: 'Settings',
  //   path: '/settings',
  //   label: '설정',
  //   icon: '⚙️'
  // },
  // {
  //   name: 'Logs',
  //   path: '/logs',
  //   label: '로그',
  //   icon: '📋'
  // }
]

const handleToggle = () => {
  emit('toggle')
}
</script>

<style scoped>
.sidebar {
  width: 250px;
  background: #2c3e50;
  color: white;
  position: fixed;
  top: 60px; /* Header 높이만큼 아래로 */
  left: 0;
  bottom: 0;
  transition: width 0.3s ease;
  overflow: hidden;
  z-index: 999;
  display: flex;
  flex-direction: column;
}

.sidebar.collapsed {
  width: 70px;
}

.toggle-btn {
  position: absolute;
  top: 50%; /* 변경: 12px → 50% (사이드바 세로 가운데) */
  right: -15px;
  background: #3498db;
  border: none;
  width: 30px;
  height: 30px;
  border-radius: 1px;
  color: white;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1001;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
  transition: all 0.2s ease;
  transform: translateY(-50%); /* 추가: 정확한 가운데 정렬 */
}

.toggle-btn:hover {
  background: #2980b9;
  transform: translateY(-50%) scale(1.1); /* 변경: 가운데 정렬 유지하면서 크기 변경 */
}

.toggle-icon {
  font-size: 12px;
}

.sidebar-nav {
  flex: 1;
  padding: 20px 0;
  overflow-y: auto;
}

.nav-list {
  list-style: none;
  margin: 0;
  padding: 0;
}

.nav-item {
  margin: 0;
}

.nav-link {
  display: flex;
  align-items: center;
  padding: 12px 20px;
  color: #bdc3c7;
  text-decoration: none;
  transition: all 0.2s ease;
  position: relative;
  gap: 12px;
  justify-content: flex-start; /* 아이콘 제거 후 정렬 조정 */
}

.nav-link:hover {
  background: rgba(52, 152, 219, 0.1);
  color: #3498db;
  padding-left: 24px;
}

.nav-link.active {
  background: #3498db;
  color: white;
}

.nav-link.active::before {
  content: '';
  position: absolute;
  right: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 3px;
  height: 20px;
}

.nav-icon {
  font-size: 20px;
  width: 24px;
  text-align: center;
  flex-shrink: 0;
}

.nav-label {
  flex: 1;
  font-size: 15px;
  font-weight: 500;
  white-space: nowrap;
}

.nav-badge {
  background: #e74c3c;
  color: white;
  font-size: 10px;
  padding: 2px 6px;
  border-radius: 10px;
  font-weight: bold;
}

.nav-divider {
  height: 1px;
  background: rgba(255, 255, 255, 0.1);
  margin: 20px 20px;
}

.nav-section {
  margin-top: 20px;
}

.nav-section-title {
  padding: 8px 20px;
  font-size: 12px;
  font-weight: 600;
  color: #95a5a6;
  text-transform: uppercase;
  letter-spacing: 1px;
  margin-bottom: 8px;
}

/* 반응형 디자인 */
@media (max-width: 768px) {
  .sidebar {
    transform: translateX(-100%);
    width: 250px;
  }

  .sidebar.collapsed {
    transform: translateX(-100%);
  }

  .sidebar.show {
    transform: translateX(0);
  }
}

/* 스크롤바 스타일 */
.sidebar-nav::-webkit-scrollbar {
  width: 4px;
}

.sidebar-nav::-webkit-scrollbar-track {
  background: rgba(0, 0, 0, 0.05); /* 변경: 검은색 투명 배경 */
}

.sidebar-nav::-webkit-scrollbar-thumb {
  background: rgba(0, 0, 0, 0.2); /* 변경: 검은색 투명 썸 */
  border-radius: 4px;
}

.sidebar-nav::-webkit-scrollbar-thumb:hover {
  background: rgba(0, 0, 0, 0.3); /* 변경: 검은색 투명 호버 */
}
</style>
