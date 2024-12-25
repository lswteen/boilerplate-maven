package com.farfarcoder.web.airfly.mapper;

import com.farfarcoder.domain.airfly.model.Airfly;
import com.farfarcoder.web.airfly.controller.dto.AirflyResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-25T10:38:35+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Homebrew)"
)
@Component
public class AirflyWebMapperImpl implements AirflyWebMapper {

    @Override
    public AirflyResponse toResponse(Airfly airfly) {
        if ( airfly == null ) {
            return null;
        }

        AirflyResponse.AirflyResponseBuilder airflyResponse = AirflyResponse.builder();

        airflyResponse.createdAt( map( airfly.createdAt() ) );
        airflyResponse.updatedAt( map( airfly.updatedAt() ) );
        airflyResponse.id( airfly.id() );
        airflyResponse.name( airfly.name() );
        airflyResponse.description( airfly.description() );
        airflyResponse.capacity( airfly.capacity() );

        return airflyResponse.build();
    }
}
