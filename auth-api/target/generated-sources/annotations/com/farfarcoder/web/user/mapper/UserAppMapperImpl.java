package com.farfarcoder.web.user.mapper;

import com.farfarcoder.domain.user.model.User;
import com.farfarcoder.web.user.controller.dto.SignUpRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-07T14:48:48+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class UserAppMapperImpl implements UserAppMapper {

    @Override
    public User toUser(SignUpRequest signUpRequest) {
        if ( signUpRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.userId( signUpRequest.getUserId() );
        user.name( signUpRequest.getName() );
        user.password( signUpRequest.getPassword() );
        user.idType( signUpRequest.getIdType() );
        user.idValue( signUpRequest.getIdValue() );

        return user.build();
    }
}
