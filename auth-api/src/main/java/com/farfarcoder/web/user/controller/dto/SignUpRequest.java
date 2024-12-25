package com.farfarcoder.web.user.controller.dto;

import com.farfarcoder.domain.user.type.UserType;
import com.farfarcoder.web.user.validator.StringName;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

@Schema(title = "회원 가입 Request")
@Builder(toBuilder = true)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
@Jacksonized
public class SignUpRequest {

    @NotBlank
    @StringName
    @Size(min=3, max=20)
    private String userId;

    @NotBlank
    @Size(min=3, max=20)
    private String name;

    @NotBlank
    @Size(min=3)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)  //직렬화 보안이슈 제외
    private String password;

    @NotNull
    private UserType idType;

    @NotBlank
    private String idValue;
}
