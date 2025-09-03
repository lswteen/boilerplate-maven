import axios from 'axios'

// API Base URL (Spring Boot 서버)
const BASE_URL = 'http://localhost:58080'

// Axios 인스턴스 생성
const apiClient = axios.create({
  baseURL: BASE_URL,
  timeout: 10000, // 10초 타임아웃
  headers: {
    'Content-Type': 'application/json',
    'Accept': 'application/json'
  }
})

// 요청 인터셉터
apiClient.interceptors.request.use(
  (config) => {
    // 요청 로깅
    console.log(`🚀 API Request: ${config.method?.toUpperCase()} ${config.url}`)

    // 인증 토큰이 있다면 헤더에 추가
    // const token = localStorage.getItem('authToken')
    // if (token && config.headers) {
    //   config.headers.Authorization = `Bearer ${token}`
    // }

    return config
  },
  (error) => {
    console.error('❌ Request Error:', error)
    return Promise.reject(error)
  }
)

// 응답 인터셉터
apiClient.interceptors.response.use(
  (response) => {
    // 응답 로깅
    console.log(`✅ API Response: ${response.status} ${response.config.url}`)
    return response
  },
  (error) => {
    // 에러 처리
    console.error('❌ Response Error:', error)

    if (error.response) {
      // 서버가 응답했지만 에러 상태코드
      const { status, data } = error.response
      console.error(`Server Error: ${status}`, data)

      // 특정 에러 처리
      switch (status) {
        case 401:
          console.error('인증 에러: 로그인이 필요합니다')
          break
        case 403:
          console.error('권한 에러: 접근 권한이 없습니다')
          break
        case 404:
          console.error('리소스 에러: 요청한 데이터를 찾을 수 없습니다')
          break
        case 500:
          console.error('서버 에러: 서버에 문제가 발생했습니다')
          break
      }
    } else if (error.request) {
      console.error('네트워크 에러: 서버에 연결할 수 없습니다')
    } else {
      console.error('요청 설정 에러:', error.message)
    }

    return Promise.reject(error)
  }
)

export default apiClient

// API 상태 확인 함수 (실제 API 엔드포인트 사용)
export const checkApiHealth = async (): Promise<boolean> => {
  try {
    // 실제 존재하는 API 엔드포인트로 테스트
    const response = await apiClient.get('/api/v1/project-projmgmt')
    return response.status === 200
  } catch (error) {
    console.error('API Health Check Failed:', error)
    return false
  }
}
