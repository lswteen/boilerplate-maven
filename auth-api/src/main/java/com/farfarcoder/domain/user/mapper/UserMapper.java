package com.farfarcoder.domain.user.mapper;


import com.farfarcoder.domain.user.entity.UserEntity;
import com.farfarcoder.domain.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper (componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {
    default UserEntity toEntity(User user, String encryptedValue){
        return UserEntity.builder()
                .userId(user.userId())
                .name(user.name())
                .password(user.password())
                .idType(user.idType())
                .idValue(encryptedValue)
                .build();
    }

    User toModel(UserEntity userEntity);

}
