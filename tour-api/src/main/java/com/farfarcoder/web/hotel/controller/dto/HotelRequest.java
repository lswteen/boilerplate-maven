package com.farfarcoder.web.hotel.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(title = "Hotel Request")
@Builder(toBuilder = true)
public record HotelRequest(
        Long id,
        String name
) {
}
