package com.farfarcoder.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class SampleController {
    @Value("${spring.application.name")
    private String applicationName;
    @GetMapping("/hello")
    public Mono<String> hello() {
        //log.info("called ".concat(applicationName));
        return Mono.just(applicationName);
    }
}
