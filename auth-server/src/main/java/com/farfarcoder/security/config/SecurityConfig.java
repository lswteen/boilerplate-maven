package com.farfarcoder.security.config;

import com.farfarcoder.security.handler.JwtAccessDeniedHandler;
import com.farfarcoder.security.provider.TokenProvider;
import com.farfarcoder.security.entrypoint.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final String[] allowedUrls = {
        "/",
        "/swagger-ui.html",
        "/swagger-ui/**",
        "/swagger-resources/**",
        "/error",
        "/v3/api-docs/**",
        "/favicon.ico",
        "/h2-console",
        "/h2-console/**",
        "/user/signup",
        "/user/login"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
            .csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler)

            //enable h2-console
            .and()
            .headers()
            .frameOptions()
            .sameOrigin()

            //session 미사용
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            .authorizeHttpRequests()    // HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정
            .requestMatchers(allowedUrls).permitAll() //url 접근허용
            .anyRequest().authenticated()

            .and()
            .apply(new JwtSecurityConfig(tokenProvider));

        return httpSecurity.build();
    }


}
