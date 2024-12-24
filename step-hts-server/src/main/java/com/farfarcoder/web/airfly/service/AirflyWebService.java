package com.farfarcoder.web.airfly.service;

import com.farfarcoder.domain.airfly.model.Airfly;
import com.farfarcoder.domain.airfly.service.AirflyService;
import com.farfarcoder.web.airfly.controller.dto.AirflyResponse;
import com.farfarcoder.web.airfly.mapper.AirflyWebMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirflyWebService {
    private final AirflyService airflyService;
    private final AirflyWebMapper airflyWebMapper;

    public List<AirflyResponse> getAllAirflies(){
        List<Airfly> airflyList =  airflyService.findAll();
        return airflyList.stream()
                .map(airflyWebMapper::toResponse)
                .collect(Collectors.toList());
    }

    public AirflyResponse getAirflyById(Long id){
        return airflyWebMapper.toResponse(airflyService.findById(id));
    }

}
