package com.farfarcoder.domain.hotel.repository.read;

import com.farfarcoder.domain.hotel.entity.HotelEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Mapper
@Repository
public interface HotelReadRepository {
    @Select("SELECT * FROM tour_hotel WHERE id = #{id}")
    Optional<HotelEntity> findById(@Param("id") Long id);

    @Select("SELECT * FROM tour_hotel")
    List<HotelEntity> findAll();
}
