package com.farfarcoder.domain.airfly.repository;

import com.farfarcoder.domain.airfly.entity.AirflyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirflyRepository extends JpaRepository<AirflyEntity, Long> {
}
