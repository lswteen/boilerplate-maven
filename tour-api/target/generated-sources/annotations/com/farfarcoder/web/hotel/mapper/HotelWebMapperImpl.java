package com.farfarcoder.web.hotel.mapper;

import com.farfarcoder.domain.hotel.model.Hotel;
import com.farfarcoder.web.hotel.controller.dto.HotelResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-25T14:16:03+0900",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250729-0351, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class HotelWebMapperImpl implements HotelWebMapper {

    @Override
    public HotelResponse toResponse(Hotel hotel) {
        if ( hotel == null ) {
            return null;
        }

        HotelResponse.HotelResponseBuilder hotelResponse = HotelResponse.builder();

        hotelResponse.createdAt( map( hotel.createdAt() ) );
        hotelResponse.updatedAt( map( hotel.updatedAt() ) );
        hotelResponse.capacity( hotel.capacity() );
        hotelResponse.description( hotel.description() );
        hotelResponse.id( hotel.id() );
        hotelResponse.name( hotel.name() );

        return hotelResponse.build();
    }
}
