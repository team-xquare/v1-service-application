package io.github.v1serviceapplication.domain.studyroom.mapper;

import io.github.v1serviceapplication.domain.studyroom.extension.domain.ExtensionEntity;
import io.github.v1serviceapplication.studyroom.extension.Extension;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExtensionMapper {

    Extension extensionEntityToDomain(ExtensionEntity extensionEntity);

}
