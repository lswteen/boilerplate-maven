package com.farfarcoder.domain.pkg.repository;

import com.farfarcoder.domain.pkg.entity.PackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepository extends JpaRepository<PackageEntity, Long> {
}
