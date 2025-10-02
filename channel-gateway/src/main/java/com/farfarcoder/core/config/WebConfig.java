package com.farfarcoder.core.config;

import com.farfarcoder.util.ObjectMapperUtils;
import io.netty.channel.ChannelOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import reactor.netty.http.HttpProtocol;
import reactor.netty.resources.LoopResources;

import java.time.Duration;

@Slf4j
@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {

    /**
     * Netty 서버 커스터마이징
     * - Idle Timeout 설정
     * - HTTP/2 지원
     * - TCP 성능 최적화
     */
    @Bean
    public NettyServerCustomizer nettyServerCustomizer() {
        return httpServer -> httpServer
                .idleTimeout(Duration.ofSeconds(60))
                // HTTP/1.1 및 HTTP/2 프로토콜 지원
                .protocol(HttpProtocol.HTTP11, HttpProtocol.H2)

                // TCP 옵션 설정
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.SO_BACKLOG, 1024)

                // 로그 설정
                .doOnConnection(conn ->
                        log.debug("New connection established: {}", conn.channel().remoteAddress())
                )

                // 에러 핸들링
                .doOnChannelInit((observer, channel, remoteAddress) -> {
                    log.debug("Channel initialized for: {}", remoteAddress);
                });
    }


    /**
     * Netty Loop Resources 커스터마이징
     * - 워커 스레드 풀 설정
     */
    @Bean
    public LoopResources loopResources() {
        return LoopResources.create("gateway-event-loop",
                Runtime.getRuntime().availableProcessors() * 2,  // worker threads
                true  // daemon threads
        );
    }


    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        configurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(ObjectMapperUtils.defaultMapper()));
        configurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(ObjectMapperUtils.defaultMapper()));
        // 메모리 제한 설정 (기본 256KB → 10MB)
        configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024);
    }

}

