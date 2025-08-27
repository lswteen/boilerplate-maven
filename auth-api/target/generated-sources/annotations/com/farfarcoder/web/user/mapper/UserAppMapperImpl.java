package com.farfarcoder.web.user.mapper;

import com.farfarcoder.domain.user.model.User;
import com.farfarcoder.web.user.controller.dto.SignUpRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-25T14:16:04+0900",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250729-0351, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class UserAppMapperImpl implements UserAppMapper {

    @Override
    public User toUser(SignUpRequest signUpRequest) {
        if ( signUpRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.idType( signUpRequest.getIdType() );
        user.idValue( signUpRequest.getIdValue() );
        user.name( signUpRequest.getName() );
        user.password( signUpRequest.getPassword() );
        user.userId( signUpRequest.getUserId() );

        return user.build();
    }
}
