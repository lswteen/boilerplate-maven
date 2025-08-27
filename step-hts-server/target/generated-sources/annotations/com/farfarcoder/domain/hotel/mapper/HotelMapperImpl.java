package com.farfarcoder.domain.hotel.mapper;

import com.farfarcoder.domain.hotel.entity.HotelEntity;
import com.farfarcoder.domain.hotel.model.Hotel;
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

        Hotel.HotelBuilder hotel = Hotel.builder();

        hotel.createdAt( hotelEntity.getCreatedAt() );
        hotel.id( hotelEntity.getId() );
        hotel.location( hotelEntity.getLocation() );
        hotel.name( hotelEntity.getName() );
        hotel.stars( hotelEntity.getStars() );
        hotel.updatedAt( hotelEntity.getUpdatedAt() );

        return hotel.build();
    }

    @Override
    public HotelEntity toEntity(Hotel hotel) {
        if ( hotel == null ) {
            return null;
        }

        HotelEntity.HotelEntityBuilder hotelEntity = HotelEntity.builder();

        hotelEntity.createdAt( hotel.createdAt() );
        hotelEntity.id( hotel.id() );
        hotelEntity.location( hotel.location() );
        hotelEntity.name( hotel.name() );
        hotelEntity.stars( hotel.stars() );
        hotelEntity.updatedAt( hotel.updatedAt() );

        return hotelEntity.build();
    }
}
