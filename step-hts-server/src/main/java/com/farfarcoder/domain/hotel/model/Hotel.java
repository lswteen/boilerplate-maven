package com.farfarcoder.domain.hotel.model;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder(toBuilder = true)
public record Hotel (
        Long id,
        String name,
        String location,
        Integer stars,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){
}
