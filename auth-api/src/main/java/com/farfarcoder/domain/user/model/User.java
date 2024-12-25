package com.farfarcoder.domain.user.model;

import com.farfarcoder.domain.user.type.UserType;
import lombok.Builder;

@Builder(toBuilder = true)
public record User(
        Long id,
        String userId,
        String name,
        String password,
        UserType idType,
        String idValue
) {
}
