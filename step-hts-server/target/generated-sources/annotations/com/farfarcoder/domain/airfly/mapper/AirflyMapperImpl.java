package com.farfarcoder.domain.airfly.mapper;

import com.farfarcoder.domain.airfly.entity.AirflyEntity;
import com.farfarcoder.domain.airfly.model.Airfly;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-07T14:40:31+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class AirflyMapperImpl implements AirflyMapper {

    @Override
    public Airfly toModel(AirflyEntity airflyEntity) {
        if ( airflyEntity == null ) {
            return null;
        }

        Airfly.AirflyBuilder airfly = Airfly.builder();

        airfly.id( airflyEntity.getId() );
        airfly.name( airflyEntity.getName() );
        airfly.description( airflyEntity.getDescription() );
        airfly.capacity( airflyEntity.getCapacity() );
        airfly.createdAt( airflyEntity.getCreatedAt() );
        airfly.updatedAt( airflyEntity.getUpdatedAt() );

        return airfly.build();
    }

    @Override
    public AirflyEntity toEntity(Airfly airfly) {
        if ( airfly == null ) {
            return null;
        }

        AirflyEntity.AirflyEntityBuilder airflyEntity = AirflyEntity.builder();

        airflyEntity.id( airfly.id() );
        airflyEntity.name( airfly.name() );
        airflyEntity.description( airfly.description() );
        airflyEntity.capacity( airfly.capacity() );
        airflyEntity.createdAt( airfly.createdAt() );
        airflyEntity.updatedAt( airfly.updatedAt() );

        return airflyEntity.build();
    }
}
