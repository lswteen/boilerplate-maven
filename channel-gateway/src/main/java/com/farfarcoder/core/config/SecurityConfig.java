package com.farfarcoder.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.header.ReferrerPolicyServerHttpHeadersWriter;
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter;

import java.time.Duration;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Value("${management.endpoints.web.base-path}")
    private String basePath;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf((csrf) -> {
                    csrf.disable();
                })
                .headers((headerSpec) -> {
                    headerSpec
                            // Referrer Policy
                            .referrerPolicy(referrer ->
                                    referrer.policy(ReferrerPolicyServerHttpHeadersWriter.ReferrerPolicy.ORIGIN))
                            // HSTS (HTTP Strict Transport Security) - HTTPS 강제
                            // 브라우저에 HTTPS만 사용하도록 지시하는 보안 헤더
                            .hsts(hsts -> hsts
                                    .maxAge(Duration.ofDays(3650))  // 10년 (최대 권장값)
                                    .includeSubdomains(true)  // 모든 서브도메인에도 적용
                                    .preload(true)  // HSTS preload 리스트에 등록 가능
                            )
                            // X-Frame-Options (Clickjacking 방지)
                            .frameOptions(frame ->
                                    frame.mode(XFrameOptionsServerHttpHeadersWriter.Mode.DENY))
                            // X-Content-Type-Options (MIME 스니핑 방지)
                            .contentTypeOptions(Customizer.withDefaults())
                            // X-XSS-Protection (브라우저 XSS 필터 활성화)
                            // 참고: 최신 브라우저는 CSP를 사용하므로 이 헤더는 레거시 지원용
                            .xssProtection(xss -> xss.disable())
                            // Content Security Policy (선택사항)
                            .contentSecurityPolicy(csp -> csp
                                    .policyDirectives("default-src 'self'; script-src 'self' 'unsafe-inline'; style-src 'self' 'unsafe-inline'")
                            );
                })
                .authorizeExchange((exchanges -> {
                    exchanges.pathMatchers(basePath + "/health", basePath + "/refresh" , basePath + "/info").permitAll();
                    exchanges.pathMatchers(basePath + "/**").authenticated();
                    exchanges.anyExchange().permitAll();
                }))
                .httpBasic(Customizer.withDefaults())
                .formLogin((formLogin) -> {
                    formLogin.disable();
                })
                .build();
    }
}
