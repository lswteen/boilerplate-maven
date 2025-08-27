package com.farfarcoder.domain.hotel.mapper;

import com.farfarcoder.domain.hotel.entity.HotelEntity;
import com.farfarcoder.domain.hotel.model.Hotel;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-25T14:16:03+0900",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250729-0351, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class HotelMapperImpl implements HotelMapper {

    @Override
    public Hotel toModel(HotelEntity hotelEntity) {
        if ( hotelEntity == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String description = null;
        Integer capacity = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;

        id = hotelEntity.getId();
        name = hotelEntity.getName();
        description = hotelEntity.getDescription();
        capacity = hotelEntity.getCapacity();
        createdAt = hotelEntity.getCreatedAt();
        updatedAt = hotelEntity.getUpdatedAt();

        Hotel hotel = new Hotel( id, name, description, capacity, createdAt, updatedAt );

        return hotel;
    }

    @Override
    public HotelEntity toEntity(Hotel hotel) {
        if ( hotel == null ) {
            return null;
        }

        HotelEntity.HotelEntityBuilder hotelEntity = HotelEntity.builder();

        hotelEntity.capacity( hotel.capacity() );
        hotelEntity.createdAt( hotel.createdAt() );
        hotelEntity.description( hotel.description() );
        hotelEntity.id( hotel.id() );
        hotelEntity.name( hotel.name() );
        hotelEntity.updatedAt( hotel.updatedAt() );

        return hotelEntity.build();
    }
}
