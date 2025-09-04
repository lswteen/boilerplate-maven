// API 응답 공통 타입
export interface ApiResponse<T> {
  data: T
  status: number
  message?: string
}

// Project-ProjMgmt 관련 타입들
export interface ProjectProjMgmtResponse {
  // Project 정보
  projectId: number
  projectKey: string
  title: string
  description: string

  // ProjMgmt 정보
  projMgmtId: number
  bizDiv: string
  bizMgr: string
  config: string
  dev: string
  oper: string
  status: string
  bitbucketName: string
  bitbucketKey: string
  bitbucketDesc: string
  bambooName: string
  bambooKey: string
  bambooDesc: string
  deployMgr: string
  createdAt: string
  updatedAt: string
}

// Project-Build 관련 타입들
export interface ProjectBuildResponse {
  // Project 정보
  projectId: number
  projectKey: string
  title: string
  description: string

  // Build 정보 리스트
  builds: BuildResponse[]
}

export interface BuildResponse {
  buildId: number
  projectId: number
  buildTitle: string
  buildType: string
  createdAt: string
  updatedAt: string
}

// NULL ProjMgmt 타입
export interface ProjMgmtDto {
  id: number
  bizDiv: string
  bizMgr: string
  config: string
  dev: string
  oper: string
  status: string
  bitbucketName: string
  bitbucketKey: string
  bitbucketDesc: string
  bambooName: string
  bambooKey: string
  bambooDesc: string
  deployMgr: string
  createdAt: string
  updatedAt: string
}

// 미매핑 프로젝트 타입
export interface ProjectResponse {
  projectId: number
  projectKey: string
  title: string
  description: string
  projMgmt: any // 미매핑이므로 null
  builds: any[] // 빌드 정보
}
