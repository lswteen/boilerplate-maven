package com.farfarcoder.scm.domain.bamboo.repository;


import com.farfarcoder.scm.domain.bamboo.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    /**
     * 모든 Project를 Build와 함께 조회 (타이틀순 정렬)
     */
    @Query("SELECT DISTINCT p FROM projects p " +
            "LEFT JOIN FETCH p.builds b " +
            "ORDER BY p.title ASC")
    List<ProjectEntity> findAllWithBuildsOrderByTitleAsc();

    /**
     * 특정 projectKey로 Project와 Build를 함께 조회
     */
    @Query("SELECT p FROM projects p " +
            "LEFT JOIN FETCH p.builds b " +
            "WHERE p.projectKey = :projectKey")
    Optional<ProjectEntity> findByProjectKeyWithBuildsOrderByTitleAsc(@Param("projectKey") String projectKey);

    /**
     * 특정 projectId로 Project와 Build를 함께 조회
     */
    @Query("SELECT p FROM projects p " +
            "LEFT JOIN FETCH p.builds b " +
            "WHERE p.projectId = :projectId")
    Optional<ProjectEntity> findByIdWithBuilds(@Param("projectId") Long projectId);
}
