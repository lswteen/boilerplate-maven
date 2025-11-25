package com.farfarcoder.core.config;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import reactor.netty.DisposableServer;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.server.HttpServer;

import javax.net.ssl.KeyManagerFactory;
import java.io.InputStream;
import java.security.KeyStore;

//@Slf4j
//@Configuration
//@ConditionalOnProperty(name = "custom.https.enabled", havingValue = "true")
//public class HttpsProxyConfig implements ApplicationListener<ApplicationReadyEvent>  {
//    @Value("${custom.https.port:8443}")
//    private int httpsPort;
//
//    @Value("${server.port:8080}")
//    private int httpPort;
//
//    @Value("${custom.https.keystore.path}")
//    private Resource keystorePath;
//
//    @Value("${custom.https.keystore.password}")
//    private String keystorePassword;
//
//    @Value("${custom.https.keystore.type:PKCS12}")
//    private String keystoreType;
//
//    private DisposableServer disposableServer;
//    private HttpClient httpClient;
//
//    @Override
//    public void onApplicationEvent(ApplicationReadyEvent event) {
//        try {
//            // SSL 컨텍스트 생성
//            SslContext sslContext = createSslContext();
//
//            // HTTP 클라이언트 생성 (8080으로 프록시)
//            httpClient = HttpClient.create()
//                    .host("localhost")
//                    .port(httpPort);
//
//            // HTTPS 서버 생성 (8443에서 listen)
//            disposableServer = HttpServer.create()
//                    .port(httpsPort)
//                    .secure(spec -> spec.sslContext(sslContext))
//                    .handle((request, response) -> {
//                        // 모든 요청을 8080 HTTP로 프록시
//                        return httpClient
//                                .headers(h -> h.set(request.requestHeaders()))
//                                .request(request.method())
//                                .uri(request.uri())
//                                .send(request.receive().retain())
//                                .response((res, content) -> {
//                                    // 응답 헤더 복사
//                                    response.status(res.status());
//                                    res.responseHeaders().forEach(entry ->
//                                            response.header(entry.getKey(),entry.getValue())
//                                    );
//
//
//                                    // 응답 바디 전달
//                                    return response.send(content.retain());
//                                });
//                    })
//                    .bindNow();
//
//            log.info(" HTTPS proxy server started on port {} → proxying to HTTP port {}",
//                    httpsPort, httpPort);
//
//        } catch (Exception e) {
//            log.error(" Failed to start HTTPS proxy server", e);
//            throw new RuntimeException("Failed to start HTTPS proxy server", e);
//        }
//    }
//
//    private SslContext createSslContext() throws Exception {
//        KeyStore keyStore = KeyStore.getInstance(keystoreType);
//        try (InputStream inputStream = keystorePath.getInputStream()) {
//            keyStore.load(inputStream, keystorePassword.toCharArray());
//        }
//
//        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
//                KeyManagerFactory.getDefaultAlgorithm()
//        );
//        keyManagerFactory.init(keyStore, keystorePassword.toCharArray());
//
//        return SslContextBuilder
//                .forServer(keyManagerFactory)
//                .build();
//    }
//}
