package com.farfarcoder.web.hotel.mapper;

import com.farfarcoder.domain.hotel.model.Hotel;
import com.farfarcoder.web.hotel.controller.dto.HotelRequest;
import com.farfarcoder.web.hotel.controller.dto.HotelResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Mapper(componentModel = "spring")
public interface HotelWebMapper {
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    HotelResponse toResponse(Hotel hotel);

    default Instant map(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.atZone(ZoneId.systemDefault()).toInstant();
    }

    default LocalDateTime map(Instant instant) {
        return instant == null ? null : instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    default HotelRequest toRequest(Hotel hotel){
        return HotelRequest.builder()
                .id(hotel.id())
                .name(hotel.name())
                .build();
    }

}
