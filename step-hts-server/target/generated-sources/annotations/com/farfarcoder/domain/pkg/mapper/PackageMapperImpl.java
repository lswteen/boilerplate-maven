package com.farfarcoder.domain.pkg.mapper;

import com.farfarcoder.domain.pkg.entity.PackageEntity;
import com.farfarcoder.domain.pkg.model.Pkg;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-25T14:16:03+0900",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250729-0351, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class PackageMapperImpl implements PackageMapper {

    @Override
    public Pkg toModel(PackageEntity packageEntity) {
        if ( packageEntity == null ) {
            return null;
        }

        Pkg.PkgBuilder pkg = Pkg.builder();

        pkg.createdAt( packageEntity.getCreatedAt() );
        pkg.details( packageEntity.getDetails() );
        pkg.id( packageEntity.getId() );
        pkg.name( packageEntity.getName() );
        pkg.price( packageEntity.getPrice() );
        pkg.updatedAt( packageEntity.getUpdatedAt() );

        return pkg.build();
    }

    @Override
    public PackageEntity toEntity(Pkg pkg) {
        if ( pkg == null ) {
            return null;
        }

        PackageEntity.PackageEntityBuilder packageEntity = PackageEntity.builder();

        packageEntity.createdAt( pkg.createdAt() );
        packageEntity.details( pkg.details() );
        packageEntity.id( pkg.id() );
        packageEntity.name( pkg.name() );
        packageEntity.price( pkg.price() );
        packageEntity.updatedAt( pkg.updatedAt() );

        return packageEntity.build();
    }
}
