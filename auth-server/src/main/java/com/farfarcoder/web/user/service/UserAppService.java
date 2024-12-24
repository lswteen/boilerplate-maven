package com.farfarcoder.web.user.service;

import com.farfarcoder.domain.user.model.User;
import com.farfarcoder.domain.user.service.UserService;
import com.farfarcoder.security.provider.TokenProvider;
import com.farfarcoder.web.user.controller.dto.*;
import com.farfarcoder.web.user.mapper.UserAppMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAppService {
    private final UserService userService;
    private final UserAppMapper userAppMapper;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;

    public UserResponse signUp(SignUpRequest signUpRequest){
        //등록 체크
        userService.checkUserExistence(signUpRequest.getUserId());

        User user = userService
            .createUser(
                userAppMapper.toUser(signUpRequest
                    ,passwordEncoder.encode(signUpRequest.getPassword())
                )
            );

        UserResponse userResponse;
        if(signUpRequest.getUserId().equals(user.userId())){
            userResponse = UserResponse.builder()
                    .commonResponse(CommonResponse.builder()
                            .resultCode(HttpStatus.OK)
                            .resultMsg(HttpStatus.OK.name())
                            .build())
                    .build();
        }else{
            userResponse = UserResponse.builder()
                    .commonResponse(CommonResponse.builder()
                            .resultCode(HttpStatus.INTERNAL_SERVER_ERROR)
                            .resultMsg(HttpStatus.INTERNAL_SERVER_ERROR.name())
                            .build())
                    .build();
        }
        return userResponse;
    }

    public TokenResponse signIn(SignInRequest signInRequest){
        // 1. ID/PW 로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(signInRequest.getUserId(), signInRequest.getPassword());

        // 2. 사용자 검증 : security >  PrincipalDetailsService > loadUserByUsername 실행 검증
        // PrincipalDetails (User model) 반환
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 해당 객체를 SecurityContextHolder 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 4.JWT 토큰 발급
        return TokenResponse.builder()
                .commonResponse(CommonResponse.builder()
                        .resultCode(HttpStatus.OK)
                        .resultMsg(HttpStatus.OK.name())
                        .build())
                .token(tokenProvider.createToken(authentication))
                .build();
    }

}
