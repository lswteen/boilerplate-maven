package com.farfarcoder.core.config;


import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;

@Slf4j
@Configuration
public class DualPortConfig {
    @Value("${custom.http.port:8888}")
    private int httpPort;

    @Value("${server.port:8443}")
    private int httpsPort;

    @Value("${custom.http.enabled:true}")
    private boolean httpEnabled;
    @Bean
    public WebServerFactoryCustomizer<NettyReactiveWebServerFactory> nettyCustomizer() {
        return factory -> {
            if (httpEnabled) {
                log.info("이중 포트 설정 활성화 - HTTP: {}, HTTPS: {}", httpPort, httpsPort);
            }
        };
    }

    // 별도 HTTP 서버 생성 (8888 포트)
    @Bean
    @ConditionalOnProperty(value = "custom.http.enabled", havingValue = "true")
    public DisposableServer httpServer() {
        log.info("HTTP 서버 시작 중... 포트: {}", httpPort);

        DisposableServer server = HttpServer.create()
                .port(httpPort)
                .handle(this::handleHttpRequest)
                .bindNow();

        log.info("HTTP 서버 시작 완료 - 포트: {}, HTTPS 리다이렉트 포트: {}", httpPort, httpsPort);
        return server;
    }

    private Mono<Void> handleHttpRequest(HttpServerRequest request, HttpServerResponse response) {
        String path = request.uri();

        // 헬스체크 엔드포인트
        if ("/actuator/health".equals(path)) {
            String healthResponse = "{\"status\":\"UP\",\"port\":" + httpPort + ",\"protocol\":\"HTTP\"}";
            return response
                    .header("Content-Type", "application/json")
                    .sendString(Mono.just(healthResponse))
                    .then();
        }

        // HTTPS 리다이렉트
        String host = request.requestHeaders().get("Host");
        if (host != null) {
            host = host.replace(":" + httpPort, ":" + httpsPort);
        } else {
            host = "localhost:" + httpsPort;
        }

        String httpsUrl = "https://" + host + path;

        return response
                .status(HttpResponseStatus.MOVED_PERMANENTLY)
                .header("Location", httpsUrl)
                .send();
    }

}
