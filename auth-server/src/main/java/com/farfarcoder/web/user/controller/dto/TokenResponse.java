package com.farfarcoder.web.user.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(title = "로그인 Response")
@Builder(toBuilder = true)
public record TokenResponse (
        CommonResponse commonResponse,
        String token

){
}
