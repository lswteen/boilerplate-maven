package com.farfarcoder.web.user.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.springframework.http.HttpStatus;

@Schema(title = "공통 Response")
@Builder(toBuilder = true)
public record CommonResponse(HttpStatus resultCode,
                             String resultMsg) {
}