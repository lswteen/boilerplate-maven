package com.farfarcoder.domain.pkg.model;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
public record Pkg(
        Long id,
        String name,
        String details,
        Double price,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
