package com.farfarcoder.web.user.mapper;

import com.farfarcoder.domain.user.model.User;
import com.farfarcoder.web.user.controller.dto.SignUpRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper (componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserAppMapper {

    default User toUser(SignUpRequest signUpRequest, String encodePassword){
        return User.builder()
                .userId(signUpRequest.getUserId())
                .name(signUpRequest.getName())
                .password(encodePassword)
                .idType(signUpRequest.getIdType())
                .idValue(signUpRequest.getIdValue())
                .build();
    }

    @Mapping(ignore = true, target="id")
    User toUser(SignUpRequest signUpRequest);

}
