package com.farfarcoder.web.airfly.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(title = "항공사 Request")
@Builder(toBuilder = true)
public record AirflyRequest (
        Long id,
        String name
){

}
