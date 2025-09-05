import { createRouter, createWebHistory } from 'vue-router'
import MainLayout from '@/layouts/MainLayout.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: MainLayout,
      children: [
        {
          path: '',
          name: 'Dashboard',
          component: () => import('@/views/DashboardView.vue'),
          meta: {
            title: '대시보드',
            icon: '📊'
          }
        },
        {
          path: '/projects',
          name: 'Projects',
          component: () => import('@/views/ProjectsView.vue'),
          meta: {
            title: '프로젝트 관리',
            icon: '📁'
          }
        },
        {
          path: '/api-test',
          name: 'ApiTest',
          component: () => import('@/components/ApiTest.vue'),
          meta: {
            title: 'API 테스트',
            icon: '🧪'
          }
        }
        // 나머지 라우트들은 다음 단계에서 추가
        // {
        //   path: '/projects',
        //   name: 'Projects',
        //   component: () => import('@/views/ProjectsView.vue'),
        //   meta: {
        //     title: '프로젝트 관리',
        //     icon: '📁'
        //   }
        // },
        // {
        //   path: '/builds',
        //   name: 'Builds',
        //   component: () => import('@/views/BuildsView.vue'),
        //   meta: {
        //     title: '빌드 관리',
        //     icon: '🔨'
        //   }
        // },
        // {
        //   path: '/settings',
        //   name: 'Settings',
        //   component: () => import('@/views/SettingsView.vue'),
        //   meta: {
        //     title: '설정',
        //     icon: '⚙️'
        //   }
        // },
        // {
        //   path: '/logs',
        //   name: 'Logs',
        //   component: () => import('@/views/LogsView.vue'),
        //   meta: {
        //     title: '로그',
        //     icon: '📋'
        //   }
        // }
      ]
    }
  ]
})

// 네비게이션 가드 - 페이지 타이틀 업데이트
router.beforeEach((to, from, next) => {
  // 페이지 타이틀 설정
  const title = to.meta?.title as string
  if (title) {
    document.title = `${title} - SCM Dashboard`
  } else {
    document.title = 'SCM Dashboard'
  }

  next()
})

export default router
