package com.farfarcoder.domain.hotel.mapper;

import com.farfarcoder.domain.hotel.entity.HotelEntity;
import com.farfarcoder.domain.hotel.model.Hotel;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-25T10:57:13+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Homebrew)"
)
@Component
public class HotelMapperImpl implements HotelMapper {

    @Override
    public Hotel toModel(HotelEntity hotelEntity) {
        if ( hotelEntity == null ) {
            return null;
        }

        Hotel.HotelBuilder hotel = Hotel.builder();

        hotel.id( hotelEntity.getId() );
        hotel.name( hotelEntity.getName() );
        hotel.location( hotelEntity.getLocation() );
        hotel.stars( hotelEntity.getStars() );
        hotel.createdAt( hotelEntity.getCreatedAt() );
        hotel.updatedAt( hotelEntity.getUpdatedAt() );

        return hotel.build();
    }

    @Override
    public HotelEntity toEntity(Hotel hotel) {
        if ( hotel == null ) {
            return null;
        }

        HotelEntity.HotelEntityBuilder hotelEntity = HotelEntity.builder();

        hotelEntity.id( hotel.id() );
        hotelEntity.name( hotel.name() );
        hotelEntity.location( hotel.location() );
        hotelEntity.stars( hotel.stars() );
        hotelEntity.createdAt( hotel.createdAt() );
        hotelEntity.updatedAt( hotel.updatedAt() );

        return hotelEntity.build();
    }
}
