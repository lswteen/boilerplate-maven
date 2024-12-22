package com.farfarcoder.web.airfly.controller;

import com.farfarcoder.web.airfly.dto.AirflyRequest;
import com.farfarcoder.web.airfly.dto.AirflyResponse;
import com.farfarcoder.web.airfly.service.AirflyWebService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AirflyController {
    private final AirflyWebService airflyWebService;

    @GetMapping("/airflies")
    public List<AirflyResponse> getAllAirflies(){
        return airflyWebService.getAllAirflies();
    }

    @GetMapping("/airflies/{id}")
    public AirflyResponse getAirflyById(@PathVariable Long id){
        return airflyWebService.getAirflyById(id);
    }

}
