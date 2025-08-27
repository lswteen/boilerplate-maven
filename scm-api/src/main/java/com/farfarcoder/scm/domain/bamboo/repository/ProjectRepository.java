package com.farfarcoder.scm.domain.bamboo.repository;


import com.farfarcoder.scm.domain.bamboo.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    List<ProjectEntity> findByProjectKeyOrderByTitleAsc(String projectKey);

    @Query("SELECT p FROM projects p ORDER BY p.title ASC")
    List<ProjectEntity> findByAllOrderByTitleAsc();

    // PROJMGMT와 함께 조회 (LEFT JOIN)
    @Query("SELECT p FROM projects p LEFT JOIN FETCH p.projMgmt ORDER BY p.title ASC")
    List<ProjectEntity> findAllWithProjMgmtOrderByTitleAsc();

    @Query("SELECT p FROM projects p LEFT JOIN FETCH p.projMgmt WHERE p.projectKey = :projectKey ORDER BY p.title ASC")
    List<ProjectEntity> findByProjectKeyWithProjMgmtOrderByTitleAsc(@Param("projectKey") String projectKey);

    // PROJMGMT가 있는 프로젝트만 조회
    @Query("SELECT p FROM projects p JOIN p.projMgmt pm ORDER BY p.title ASC")
    List<ProjectEntity> findProjectsWithProjMgmtOrderByTitleAsc();

    // PROJMGMT가 없는 프로젝트만 조회
    @Query("SELECT p FROM projects p WHERE p.projMgmt IS NULL ORDER BY p.title ASC")
    List<ProjectEntity> findProjectsWithoutProjMgmtOrderByTitleAsc();
}
