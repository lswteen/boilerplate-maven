package com.farfarcoder.domain.user.mapper;

import com.farfarcoder.domain.user.entity.UserEntity;
import com.farfarcoder.domain.user.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-25T08:21:21+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Homebrew)"
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
        user.userId( userEntity.getUserId() );
        user.name( userEntity.getName() );
        user.password( userEntity.getPassword() );
        user.idType( userEntity.getIdType() );
        user.idValue( userEntity.getIdValue() );

        return user.build();
    }
}
