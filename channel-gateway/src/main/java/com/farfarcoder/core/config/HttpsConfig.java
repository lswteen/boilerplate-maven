package com.farfarcoder.core.config;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.DispatcherHandler;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import reactor.netty.http.server.HttpServer;

import javax.net.ssl.KeyManagerFactory;
import java.io.InputStream;
import java.security.KeyStore;

@Slf4j
@Configuration
public class HttpsConfig {
    @Bean
    public ApplicationRunner startHttpsServer(DispatcherHandler dispatcherHandler) {
        return args -> {
            // HttpHandler를 ReactorHttpHandlerAdapter로 래핑
            var httpHandler = WebHttpHandlerBuilder.webHandler(dispatcherHandler).build();
            var reactorAdapter = new ReactorHttpHandlerAdapter(httpHandler);

            // HTTPS 서버 8443 포트로 시작
            HttpServer.create()
                    .port(8443)
                    .secure(spec -> spec.sslContext(createSslContext()))
                    .handle(reactorAdapter)  // ReactorHttpHandlerAdapter 사용
                    .bindNow();

            log.info("HTTPS Server started on port: 8443");
        };
    }

    private SslContext createSslContext() {
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            try (InputStream stream = getClass().getClassLoader().getResourceAsStream("keystore.p12")) {
                keyStore.load(stream, "changeit".toCharArray());
            }

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(keyStore, "changeit".toCharArray());

            return SslContextBuilder.forServer(kmf).build();

        } catch (Exception e) {
            throw new RuntimeException("SSL Context 생성 실패", e);
        }
    }
}
