package com.farfarcoder.scm.domain.bamboo.repository;

import com.farfarcoder.scm.domain.bamboo.entity.ProjMgmtEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjMgmtRepository extends JpaRepository<ProjMgmtEntity, Long> {
    /**
     * bambookey가 'NULL' 문자열인 데이터만 DTO로 조회
     * Entity 매핑 관계를 완전히 우회하여 N+1 문제 해결
     */
    @Query(value = "SELECT " +
            "pm.id as id, " +
            "pm.bizdiv as bizDiv, " +
            "pm.bizmgr as bizMgr, " +
            "pm.config as config, " +
            "pm.dev as dev, " +
            "pm.oper as oper, " +
            "pm.status as status, " +
            "pm.bitbucketname as bitbucketName, " +
            "pm.bitbucketkey as bitbucketKey, " +
            "pm.bitbucketdesc as bitbucketDesc, " +
            "pm.bambooname as bambooName, " +
            "pm.bambookey as bambooKey, " +
            "pm.bamboodesc as bambooDesc, " +
            "pm.deploymgr as deployMgr, " +
            "pm.createdat as createdAt, " +
            "pm.updatedat as updatedAt " +
            "FROM PUBLIC.PROJMGMT pm " +
            "WHERE pm.bambookey = 'NULL' " +
            "ORDER BY pm.id DESC",
            nativeQuery = true)
    List<Object[]> findNullBambooKeyAsDto();

    // 기존 Entity 반환 메소드들 (N+1 문제 있음)
    @Query(value = "SELECT pm.* FROM PUBLIC.PROJMGMT pm WHERE pm.bambookey = 'NULL' ORDER BY pm.id DESC",
            nativeQuery = true)
    List<ProjMgmtEntity> findByBambooKeyIsNullString();

    List<ProjMgmtEntity> findAllByOrderByBambooKeyDesc();

    Optional<ProjMgmtEntity> findByBambooKey(String bambooKey);

    @Query("SELECT pm FROM projmgmt pm WHERE pm.projectEntity IS NULL ORDER BY pm.bambooKey DESC")
    List<ProjMgmtEntity> findOrphanProjMgmtOrderByBambooKeyDesc();
}
