import apiClient from './api'
import type {
  ProjectProjMgmtResponse,
  ProjectBuildResponse,
  ProjMgmtDto,
  ProjectResponse
} from '@/types/api'

export class ProjectService {
  // 🎯 Project-ProjMgmt API 관련

  /**
   * 매핑된 모든 프로젝트-프로젝트관리 조회
   */
  static async getAllProjectsWithProjMgmt(): Promise<ProjectProjMgmtResponse[]> {
    const response = await apiClient.get<ProjectProjMgmtResponse[]>('/api/v1/project-projmgmt')
    return response.data
  }

  /**
   * 프로젝트 키로 매핑된 프로젝트-프로젝트관리 조회
   */
  static async getProjectWithProjMgmtByKey(projectKey: string): Promise<ProjectProjMgmtResponse> {
    const response = await apiClient.get<ProjectProjMgmtResponse>(
      `/api/v1/project-projmgmt/by-project-key/${projectKey}`
    )
    return response.data
  }

  /**
   * Bamboo 키로 매핑된 프로젝트-프로젝트관리 조회
   */
  static async getProjectWithProjMgmtByBambooKey(bambooKey: string): Promise<ProjectProjMgmtResponse> {
    const response = await apiClient.get<ProjectProjMgmtResponse>(
      `/api/v1/project-projmgmt/by-bamboo-key/${bambooKey}`
    )
    return response.data
  }

  /**
   * 매핑되지 않은 프로젝트들 조회
   */
  static async getUnmappedProjects(): Promise<ProjectResponse[]> {
    const response = await apiClient.get<ProjectResponse[]>('/api/v1/project-projmgmt/unmapped')
    return response.data
  }

  // 🎯 Project-Build API 관련

  /**
   * 모든 프로젝트-빌드 조회
   */
  static async getAllProjectsWithBuilds(): Promise<ProjectBuildResponse[]> {
    const response = await apiClient.get<ProjectBuildResponse[]>('/api/v1/project-builds')
    return response.data
  }

  /**
   * 프로젝트 키로 프로젝트-빌드 조회
   */
  static async getProjectWithBuildsByKey(projectKey: string): Promise<ProjectBuildResponse> {
    const response = await apiClient.get<ProjectBuildResponse>(
      `/api/v1/project-builds/by-project-key/${projectKey}`
    )
    return response.data
  }

  /**
   * 프로젝트 ID로 프로젝트-빌드 조회
   */
  static async getProjectWithBuildsById(projectId: number): Promise<ProjectBuildResponse> {
    const response = await apiClient.get<ProjectBuildResponse>(
      `/api/v1/project-builds/by-project-id/${projectId}`
    )
    return response.data
  }

  /**
   * 검색 파라미터로 프로젝트-빌드 조회
   */
  static async searchProjectsWithBuilds(projectKey?: string): Promise<ProjectBuildResponse[]> {
    const params = projectKey ? { projectKey } : {}
    const response = await apiClient.get<ProjectBuildResponse[]>('/api/v1/project-builds/search', {
      params
    })
    return response.data
  }

  // 🎯 NULL ProjMgmt API 관련

  /**
   * bambookey가 'NULL'인 프로젝트관리 조회 (Entity - N+1 발생)
   */
  static async getNullProjMgmtEntities(): Promise<ProjMgmtDto[]> {
    const response = await apiClient.get<ProjMgmtDto[]>('/api/v1/null-projmgmt')
    return response.data
  }

  /**
   * bambookey가 'NULL'인 프로젝트관리 조회 (DTO - N+1 해결)
   */
  static async getNullProjMgmtDtos(): Promise<ProjMgmtDto[]> {
    const response = await apiClient.get<ProjMgmtDto[]>('/api/v1/null-projmgmt/dto')
    return response.data
  }

  // 🎯 Excel 다운로드 API 관련

  /**
   * 프로젝트-프로젝트관리 Excel 다운로드
   */
  static async downloadProjectProjMgmtExcel(): Promise<Blob> {
    const response = await apiClient.get('/api/v1/excel/projectWithProjMgmt', {
      responseType: 'blob'
    })
    return response.data
  }

  /**
   * NULL 프로젝트관리 Excel 다운로드
   */
  static async downloadNullProjMgmtExcel(): Promise<Blob> {
    const response = await apiClient.get('/api/v1/excel/projMgmtByNull', {
      responseType: 'blob'
    })
    return response.data
  }

  /**
   * 프로젝트-빌드 Excel 다운로드
   */
  static async downloadProjectBuildsExcel(): Promise<Blob> {
    const response = await apiClient.get('/api/v1/excel/projectWithBuilds', {
      responseType: 'blob'
    })
    return response.data
  }

  /**
   * 매핑되지 않은 프로젝트 Excel 다운로드
   */
  static async downloadUnmappedProjectsExcel(): Promise<Blob> {
    const response = await apiClient.get('/api/v1/excel/projectsNotMappedToProjMgmt', {
      responseType: 'blob'
    })
    return response.data
  }

}

// 기본 내보내기
export default ProjectService
