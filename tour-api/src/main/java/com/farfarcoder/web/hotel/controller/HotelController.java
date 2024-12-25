package com.farfarcoder.web.hotel.controller;

import com.farfarcoder.web.hotel.controller.dto.HotelResponse;
import com.farfarcoder.web.hotel.service.HotelWebService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tour")
public class HotelController {
    private final HotelWebService hotelWebService;

    @GetMapping("/hotels")
    public List<HotelResponse> getAllHotels(){
        return hotelWebService.getAllHotels();
    }

}
