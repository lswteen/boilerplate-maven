package com.farfarcoder.domain.user.mapper;

import com.farfarcoder.domain.user.entity.UserEntity;
import com.farfarcoder.domain.user.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-25T14:16:03+0900",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250729-0351, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toModel(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( userEntity.getId() );
        user.idType( userEntity.getIdType() );
        user.idValue( userEntity.getIdValue() );
        user.name( userEntity.getName() );
        user.password( userEntity.getPassword() );
        user.userId( userEntity.getUserId() );

        return user.build();
    }
}
