package com.farfarcoder.domain.airfly.model;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
public record Airfly (
        Long id,
        String name,
        String description,
        Integer  capacity,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){
}
