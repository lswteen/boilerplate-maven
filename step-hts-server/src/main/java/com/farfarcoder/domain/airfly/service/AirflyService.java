package com.farfarcoder.domain.airfly.service;

import com.farfarcoder.domain.airfly.entity.AirflyEntity;
import com.farfarcoder.domain.airfly.mapper.AirflyMapper;
import com.farfarcoder.domain.airfly.model.Airfly;
import com.farfarcoder.domain.airfly.repository.AirflyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirflyService {
    private final AirflyRepository airflyRepository;
    private final AirflyMapper airflyMapper;

    public List<Airfly> findAll() {
        List<AirflyEntity> entities = airflyRepository.findAll();
        return entities.stream()
                .map(airflyMapper::toModel)
                .collect(Collectors.toList());
    }

    public Airfly findById(Long id) {
        AirflyEntity entity = airflyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("항공 정보가 없습니다."));
        return airflyMapper.toModel(entity);

    }
}
