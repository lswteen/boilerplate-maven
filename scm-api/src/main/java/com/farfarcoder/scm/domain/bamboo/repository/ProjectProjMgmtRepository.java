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
     * ProjMgmt와 매핑되지 않은 모든 Project 조회
     * Project 테이블에는 총 104개가 있고, 82개가 매핑되어 있으므로 나머지 22개를 조회
     * bambookey가 'NULL' 문자열인 경우는 매핑으로 간주하지 않음
     */
    @Query(value = "SELECT p.* FROM PUBLIC.PROJECT p " +
            "LEFT JOIN PUBLIC.PROJMGMT pm ON p.project_key = pm.bambookey " +
            "AND pm.bambookey IS NOT NULL " +
            "AND pm.bambookey != 'NULL' " +
            "WHERE pm.bambookey IS NULL " +
            "ORDER BY p.project_id DESC",
            nativeQuery = true)
    List<ProjectEntity> findProjectsNotMappedToProjMgmt();

    /**
     * ProjMgmt와 매핑되지 않은 Project 개수 조회
     */
    @Query(value = "SELECT COUNT(*) FROM PUBLIC.PROJECT p " +
            "WHERE p.project_key NOT IN (" +
            "    SELECT pm.bambookey FROM PUBLIC.PROJMGMT pm " +
            "    WHERE pm.bambookey IS NOT NULL AND pm.bambookey != 'NULL'" +
            ")",
            nativeQuery = true)
    Long countProjectsNotMappedToProjMgmt();

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
