package com.farfarcoder.domain.hotel.repository;

import com.farfarcoder.domain.hotel.entity.HotelEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
@Mapper
public interface HotelRepository {
    @Select("SELECT * FROM tour_hotel WHERE id = #{id}")
    Optional<HotelEntity> findById(@Param("id") Long id);

    @Select("SELECT * FROM tour_hotel")
    List<HotelEntity> findAll();


    @Insert("""
            INSERT INTO tour_hotel(name, location, stars, created_at, updated_at)
            VALUES(#{name}, #{location}, #{stars}, NOW(), NOW())
            """)
    void insert(HotelEntity hotelEntity);

    @Update("""
            UPDATE tour_hotel
            SET name=#{name}, location = #{location}, stars = #{stars}, updated_at = NOW()
            WHERE id = #{id}
            """)
    void update(HotelEntity hotelEntity);

    @Delete("DELETE FROM tour_hotel WHERE id = #{id}")
    void deleteById(@Param("id") Long id);
}
