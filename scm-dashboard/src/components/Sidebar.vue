<template>
  <aside class="sidebar" :class="{ 'collapsed': isCollapsed }">
    <!-- 토글 버튼 -->
    <button class="toggle-btn" @click="handleToggle">
      <span class="toggle-icon">{{ isCollapsed ? '▶️' : '◀️' }}</span>
    </button>

    <!-- 네비게이션 메뉴 -->
    <nav class="sidebar-nav">
      <ul class="nav-list">
        <li v-for="item in navigationItems" :key="item.name" class="nav-item">
          <router-link
            :to="item.path"
            class="nav-link"
            :class="{ 'active': $route.name === item.name }"
            :title="isCollapsed ? item.label : ''"
          >
            <span class="nav-icon">{{ item.icon }}</span>
            <span class="nav-label" v-show="!isCollapsed">{{ item.label }}</span>
            <span class="nav-badge" v-if="item.badge && !isCollapsed">{{ item.badge }}</span>
          </router-link>
        </li>
      </ul>

      <!-- 구분선 -->
      <div class="nav-divider" v-show="!isCollapsed"></div>

      <!-- 관리 메뉴 -->
      <div class="nav-section" v-show="!isCollapsed">
        <h3 class="section-title">관리</h3>
        <ul class="nav-list">
          <li v-for="item in managementItems" :key="item.name" class="nav-item">
            <router-link
              :to="item.path"
              class="nav-link"
              :class="{ 'active': $route.name === item.name }"
            >
              <span class="nav-icon">{{ item.icon }}</span>
              <span class="nav-label">{{ item.label }}</span>
            </router-link>
          </li>
        </ul>
      </div>
    </nav>

    <!-- 하단 정보 -->
    <div class="sidebar-footer" v-show="!isCollapsed">
      <div class="footer-info">
        <div class="info-item">
          <span class="info-label">버전</span>
          <span class="info-value">v1.0.0</span>
        </div>
        <div class="info-item">
          <span class="info-label">환경</span>
          <span class="info-value">DEV</span>
        </div>
      </div>
    </div>
  </aside>
</template>

<script setup lang="ts">
import { computed } from 'vue'

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

// 메인 네비게이션 메뉴 (현재 구현된 것만)
const navigationItems: NavigationItem[] = [
  {
    name: 'Dashboard',
    path: '/',
    label: '대시보드',
    icon: '📊'
  },
  {
    name: 'ApiTest',
    path: '/api-test',
    label: 'API 테스트',
    icon: '🧪',
    badge: 'NEW'
  }
  // 다음 단계에서 추가될 메뉴들
  // {
  //   name: 'Projects',
  //   path: '/projects',
  //   label: '프로젝트 관리',
  //   icon: '📁'
  // },
  // {
  //   name: 'Builds',
  //   path: '/builds',
  //   label: '빌드 관리',
  //   icon: '🔨'
  // }
]

// 관리 메뉴 (임시로 비활성화)
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
  top: 12px;
  right: -15px;
  background: #3498db;
  border: none;
  width: 30px;
  height: 30px;
  border-radius: 50%;
  color: white;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1001;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
  transition: all 0.2s ease;
}

.toggle-btn:hover {
  background: #2980b9;
  transform: scale(1.1);
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
}

.nav-link:hover {
  background: rgba(52, 152, 219, 0.1);
  color: #3498db;
  padding-left: 24px;
}

.nav-link.active {
  background: #3498db; /* 그라디언트 제거 */
  color: white;
  border-left: 3px solid #e74c3c;
}

.nav-link.active::before {
  content: '';
  position: absolute;
  right: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 3px;
  height: 20px;
  background: #e74c3c;
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

.section-title {
  color: #7f8c8d;
  font-size: 12px;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 1px;
  padding: 0 20px 10px;
  margin: 0;
}

.sidebar-footer {
  padding: 20px;
  border-top: 1px solid var(--card-border);
  background: var(--darker-bg);
  margin-top: auto;
}

.footer-info {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.info-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 12px;
}

.info-label {
  color: var(--text-muted);
  text-transform: uppercase;
  letter-spacing: 0.5px;
  font-weight: 600;
}

.info-value {
  color: var(--text-secondary);
  font-weight: 600;
  padding: 2px 8px;
  background: var(--card-bg);
  border-radius: 4px;
}

/* 반응형 디자인 */
@media (max-width: 768px) {
  .sidebar {
    width: 250px;
    transform: translateX(-100%);
    transition: transform 0.3s ease;
  }

  .sidebar.collapsed {
    transform: translateX(-100%);
    width: 250px;
  }

  .sidebar.open {
    transform: translateX(0);
  }

  .toggle-btn {
    display: none;
  }
}

/* 스크롤바 스타일링 */
.sidebar-nav::-webkit-scrollbar {
  width: 4px;
}

.sidebar-nav::-webkit-scrollbar-track {
  background: transparent;
}

.sidebar-nav::-webkit-scrollbar-thumb {
  background: rgba(255, 255, 255, 0.2);
  border-radius: 2px;
}

.sidebar-nav::-webkit-scrollbar-thumb:hover {
  background: rgba(255, 255, 255, 0.3);
}
</style>
