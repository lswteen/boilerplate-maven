package com.farfarcoder.scm.domain.bamboo.mapper;

import com.farfarcoder.scm.domain.bamboo.entity.ProjectEntity;
import com.farfarcoder.scm.domain.bamboo.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ProjectMapper {
    Project toModel(ProjectEntity projectEntity);
}
