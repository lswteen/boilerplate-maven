package com.farfarcoder.scm.domain.bamboo.repository;

import com.farfarcoder.scm.domain.bamboo.entity.ProjMgmtEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjMgmtRepository extends JpaRepository<ProjMgmtEntity, Long> {
    // PROJECT가 없는 PROJMGMT (orphan 데이터)
    @Query("SELECT pm FROM projmgmt pm WHERE pm.projectEntity IS NULL ORDER BY pm.bambooKey DESC")
    List<ProjMgmtEntity> findOrphanProjMgmtOrderByBambooKeyDesc();

    // 전체 조회
    List<ProjMgmtEntity> findAllByOrderByBambooKeyDesc();

    // BAMBOO_KEY로 조회
    Optional<ProjMgmtEntity> findByBambooKey(String bambooKey);



}
