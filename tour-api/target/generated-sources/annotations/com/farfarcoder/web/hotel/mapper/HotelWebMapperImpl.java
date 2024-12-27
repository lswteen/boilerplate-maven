package com.farfarcoder.web.hotel.mapper;

import com.farfarcoder.domain.hotel.model.Hotel;
import com.farfarcoder.web.hotel.controller.dto.HotelResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-28T08:14:35+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Amazon.com Inc.)"
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
        hotelResponse.id( hotel.id() );
        hotelResponse.name( hotel.name() );
        hotelResponse.description( hotel.description() );
        hotelResponse.capacity( hotel.capacity() );

        return hotelResponse.build();
    }
}
