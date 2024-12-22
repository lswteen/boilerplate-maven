package com.farfarcoder.domain.pkg.service;

import com.farfarcoder.domain.pkg.entity.PackageEntity;
import com.farfarcoder.domain.pkg.model.Pkg;
import com.farfarcoder.domain.pkg.repository.PackageRepository;
import com.farfarcoder.domain.pkg.mapper.PackageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PackageService {
    private final PackageRepository packageRepository;
    private final PackageMapper packageMapper;

    public List<Pkg> findAll(){
        List<PackageEntity> entities = packageRepository.findAll();
        return entities.stream()
                .map(packageMapper::toModel)
                .collect(Collectors.toList());
    }

    public Pkg findById(Long id){
        PackageEntity entity = packageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("패키지 정보가 없습니다."));
        return packageMapper.toModel(entity);
    }
}
