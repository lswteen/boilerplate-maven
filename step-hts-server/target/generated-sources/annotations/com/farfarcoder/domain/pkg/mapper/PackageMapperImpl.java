package com.farfarcoder.domain.pkg.mapper;

import com.farfarcoder.domain.pkg.entity.PackageEntity;
import com.farfarcoder.domain.pkg.model.Pkg;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-23T21:33:01+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Homebrew)"
)
@Component
public class PackageMapperImpl implements PackageMapper {

    @Override
    public Pkg toModel(PackageEntity packageEntity) {
        if ( packageEntity == null ) {
            return null;
        }

        Pkg.PkgBuilder pkg = Pkg.builder();

        pkg.id( packageEntity.getId() );
        pkg.name( packageEntity.getName() );
        pkg.details( packageEntity.getDetails() );
        pkg.price( packageEntity.getPrice() );
        pkg.createdAt( packageEntity.getCreatedAt() );
        pkg.updatedAt( packageEntity.getUpdatedAt() );

        return pkg.build();
    }

    @Override
    public PackageEntity toEntity(Pkg pkg) {
        if ( pkg == null ) {
            return null;
        }

        PackageEntity.PackageEntityBuilder packageEntity = PackageEntity.builder();

        packageEntity.id( pkg.id() );
        packageEntity.name( pkg.name() );
        packageEntity.details( pkg.details() );
        packageEntity.price( pkg.price() );
        packageEntity.createdAt( pkg.createdAt() );
        packageEntity.updatedAt( pkg.updatedAt() );

        return packageEntity.build();
    }
}
