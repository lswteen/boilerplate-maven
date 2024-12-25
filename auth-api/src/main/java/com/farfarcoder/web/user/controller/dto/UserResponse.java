package com.farfarcoder.web.user.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(title = "회원 가입 Response")
@Builder(toBuilder = true)
public record UserResponse (
        CommonResponse commonResponse
        ){
}
