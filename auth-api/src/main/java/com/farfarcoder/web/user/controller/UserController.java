package com.farfarcoder.web.user.controller;


import com.farfarcoder.web.user.controller.dto.SignInRequest;
import com.farfarcoder.web.user.controller.dto.SignUpRequest;
import com.farfarcoder.web.user.controller.dto.TokenResponse;
import com.farfarcoder.web.user.controller.dto.UserResponse;
import com.farfarcoder.web.user.service.UserAppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "User", description = "User 기능 제공")
public class UserController {
    private final UserAppService userAppService;
    @Operation(
        description = "회원 가입"
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/user/signup")
    public UserResponse singUp(@Valid @RequestBody SignUpRequest signUpRequest){
        return userAppService.signUp(signUpRequest);
    }

    @Operation(
        description = "로그인 이후 JWT Token 발급"
    )
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/user/login")
    public TokenResponse signIn(@Valid @RequestBody SignInRequest signInRequest){
        return userAppService.signIn(signInRequest);
    }

}
