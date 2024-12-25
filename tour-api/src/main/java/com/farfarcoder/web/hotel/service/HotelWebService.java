package com.farfarcoder.web.hotel.service;

import com.farfarcoder.domain.hotel.model.Hotel;
import com.farfarcoder.domain.hotel.service.HotelService;
import com.farfarcoder.web.hotel.controller.dto.HotelResponse;
import com.farfarcoder.web.hotel.mapper.HotelWebMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelWebService {
    private final HotelService hotelService;
    private final HotelWebMapper hotelWebMapper;

    public List<HotelResponse> getAllHotels(){
        List<Hotel> hotels =  hotelService.findAll();
        return hotels.stream()
                .map(hotelWebMapper::toResponse)
                .collect(Collectors.toList());
    }
}
