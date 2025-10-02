package com.farfarcoder.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import reactor.netty.http.server.HttpServer;

/**
 * HTTP(8080) 요청을 HTTPS(8443)로 리다이렉트하는 설정
 *
 * application.yml에서 활성화:
 * server:
 *   http:
 *     redirect:
 *       enabled: true
 *       port: 8080
 */

@Slf4j
@Configuration
@ConditionalOnProperty(name = "server.http.redirect.enabled", havingValue = "true", matchIfMissing = false)
public class HttpRedirectConfig {
    @Value("${server.http.redirect.port:8080}")
    private int httpPort;

    @Value("${server.port:8443}")
    private int httpsPort;


    /**
     * HTTP 포트에서 리스닝하여 HTTPS로 리다이렉트
     */
    @Bean
    public NettyServerCustomizer httpToHttpsRedirect() {
        return httpServer -> httpServer
                .port(httpPort)
                .route(routes ->
                        routes.get("/**", (request, response) -> {
                            String host = request.requestHeaders().get("Host");
                            if (host != null) {
                                // 포트 제거 후 HTTPS 포트로 변경
                                host = host.split(":")[0];
                            }

                            String redirectUrl = String.format("https://%s:%d%s",
                                    host != null ? host : "localhost",
                                    httpsPort,
                                    request.uri()
                            );

                            log.info("Redirecting HTTP request to HTTPS: {} -> {}", request.uri(), redirectUrl);

                            response.status(HttpStatus.MOVED_PERMANENTLY.value())
                                    .header("Location", redirectUrl);

                            return response.send();
                        })
                );
    }

    /**
     * 추가 Netty 서버 인스턴스 생성 (HTTP 리다이렉트용)
     *
     * 주의: 이 방식은 단일 포트만 지원하는 환경에서는 작동하지 않습니다.
     * 실제 운영 환경에서는 로드밸런서나 Nginx에서 리다이렉트를 처리하는 것을 권장합니다.
     */
    @Bean
    public NettyReactiveWebServerFactory nettyReactiveWebServerFactory() {
        NettyReactiveWebServerFactory factory = new NettyReactiveWebServerFactory();

        factory.addServerCustomizers(httpServer -> {
            return HttpServer.create()
                    .port(httpPort)
                    .route(routes ->
                            routes.get("/**", (request, response) -> {
                                String host = request.requestHeaders().get("Host");
                                if (host != null) {
                                    host = host.split(":")[0];
                                }

                                String redirectUrl = String.format("https://%s:%d%s",
                                        host != null ? host : "localhost",
                                        httpsPort,
                                        request.uri()
                                );

                                log.debug("HTTP->HTTPS redirect: {}", redirectUrl);

                                response.status(HttpStatus.PERMANENT_REDIRECT.value())
                                        .header("Location", redirectUrl);

                                return response.send();
                            })
                    );
        });

        return factory;
    }
}
