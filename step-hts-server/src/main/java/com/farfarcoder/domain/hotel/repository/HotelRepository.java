package com.farfarcoder.domain.hotel.repository;

import com.farfarcoder.domain.hotel.entity.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<HotelEntity, Long> {
}
