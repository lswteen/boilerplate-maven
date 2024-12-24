package com.farfarcoder.web.airfly.mapper;

import com.farfarcoder.domain.airfly.model.Airfly;
import com.farfarcoder.web.airfly.controller.dto.AirflyRequest;
import com.farfarcoder.web.airfly.controller.dto.AirflyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Mapper(componentModel = "spring")
public interface AirflyWebMapper {

    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    AirflyResponse toResponse(Airfly airfly);

    default Instant map(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.atZone(ZoneId.systemDefault()).toInstant();
    }

    default LocalDateTime map(Instant instant) {
        return instant == null ? null : instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    default AirflyRequest toRequest(Airfly airfly){
        return AirflyRequest.builder()
                .id(airfly.id())
                .name(airfly.name())
                .build();
    }

}
