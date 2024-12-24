package com.farfarcoder.web.user.controller.dto;

import com.farfarcoder.web.user.validator.StringName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

@Schema(title = "로그인 Request")
@Builder(toBuilder = true)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
@Jacksonized
public class SignInRequest {

    @NotBlank
    @StringName
    @Size(min=3, max=20, message="최소 3이상 20이하만 가능합니다.")
    private String userId;

    @NotNull
    @Size(min=3)
    private String password;
}
