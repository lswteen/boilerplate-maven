package com.farfarcoder.domain.airfly.mapper;

import com.farfarcoder.domain.airfly.entity.AirflyEntity;
import com.farfarcoder.domain.airfly.model.Airfly;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AirflyMapper {
    Airfly toModel(AirflyEntity airflyEntity);

    AirflyEntity toEntity(Airfly airfly);

}
