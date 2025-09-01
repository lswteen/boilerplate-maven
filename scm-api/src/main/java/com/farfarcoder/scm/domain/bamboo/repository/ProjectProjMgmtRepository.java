package com.farfarcoder.scm.domain.bamboo.repository;

import com.farfarcoder.scm.domain.bamboo.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectProjMgmtRepository extends JpaRepository<ProjectEntity, Long> {

    /**
     * Project와 ProjMgmt가 매핑된 모든 데이터 조회
     */
    @Query("SELECT p FROM projects p " +
            "JOIN FETCH p.projMgmt pm")
    List<ProjectEntity> findAllProjectsWithProjMgmt();

    /**
     * 특정 projectKey로 Project와 ProjMgmt 매핑 데이터 조회
     */
    @Query(value = "SELECT p.* FROM PUBLIC.PROJECT p " +
            "INNER JOIN PUBLIC.PROJMGMT pm ON p.project_key = pm.bambookey " +
            "WHERE p.project_key = ?1",
            nativeQuery = true)
    Optional<ProjectEntity> findProjectWithProjMgmtByProjectKey(String projectKey);

    /**
     * 특정 bambooKey로 Project와 ProjMgmt 매핑 데이터 조회
     */
    @Query(value = "SELECT p.* FROM PUBLIC.PROJECT p " +
            "INNER JOIN PUBLIC.PROJMGMT pm ON p.project_key = pm.bambookey " +
            "WHERE pm.bambookey = ?1",
            nativeQuery = true)
    Optional<ProjectEntity> findProjectWithProjMgmtByBambooKey(String bambooKey);
}
