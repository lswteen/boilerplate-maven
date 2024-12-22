package com.farfarcoder.domain.pkg.mapper;

import com.farfarcoder.domain.pkg.entity.PackageEntity;
import com.farfarcoder.domain.pkg.model.Pkg;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PackageMapper {
    Pkg toModel(PackageEntity packageEntity);
    PackageEntity toEntity(Pkg pkg);
}
