package com.farfarcoder.domain.hotel.service;

import com.farfarcoder.domain.hotel.entity.HotelEntity;
import com.farfarcoder.domain.hotel.mapper.HotelMapper;
import com.farfarcoder.domain.hotel.model.Hotel;
import com.farfarcoder.domain.hotel.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    public List<Hotel> findAll(){
        List<HotelEntity> entities = hotelRepository.findAll();
        return entities.stream()
                .map(hotelMapper::toModel)
                .collect(Collectors.toList());
    }

    public Hotel findById(Long id){
        HotelEntity entity = hotelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("항공 정보가 없습니다."));
        return hotelMapper.toModel(entity);
    }
}
