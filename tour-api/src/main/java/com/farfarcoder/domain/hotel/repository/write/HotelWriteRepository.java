package com.farfarcoder.domain.hotel.repository.write;

import com.farfarcoder.domain.hotel.entity.HotelEntity;
import org.apache.ibatis.annotations.*;

@Mapper
public interface HotelWriteRepository {

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
