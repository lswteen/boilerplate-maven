package com.farfarcoder.scm.domain.bamboo.repository;

import com.farfarcoder.scm.domain.bamboo.entity.BuildEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildRepository extends JpaRepository<BuildEntity, Long> {
    List<BuildEntity> findByProjectIdAndBuildTypeOrderByBuildIdAsc(Long projectId, String buildType);
}
