package com.farfarcoder.domain.hotel.service;

import com.farfarcoder.domain.hotel.entity.HotelEntity;
import com.farfarcoder.domain.hotel.mapper.HotelMapper;
import com.farfarcoder.domain.hotel.model.Hotel;
import com.farfarcoder.domain.hotel.repository.read.HotelReadRepository;
import com.farfarcoder.domain.hotel.repository.write.HotelWriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelReadRepository hotelReadRepository;
    private final HotelWriteRepository hotelWriteRepository;
    private final HotelMapper hotelMapper;

    public Hotel findById(Long id){
        HotelEntity entity = hotelReadRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("호텔 정보가 없습니다."));
        return hotelMapper.toModel(entity);
    }

    public List<Hotel> findAll(){
        List<HotelEntity> entities =  hotelReadRepository.findAll();
        return entities.stream()
                .map(hotelMapper::toModel)
                .collect(Collectors.toList());
    }

    public void save(Hotel hotel){
        hotelWriteRepository.insert(hotelMapper.toEntity(hotel));
    }

    public void update(Hotel hotel){
        hotelWriteRepository.update(hotelMapper.toEntity(hotel));
    }
}
