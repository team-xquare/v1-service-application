package io.github.v1serviceapplication.domain.studyroom.mapper;

import io.github.v1serviceapplication.domain.studyroom.extension.domain.ExtensionEntity;
import io.github.v1serviceapplication.studyroom.extension.Extension;
import org.springframework.stereotype.Component;

@Component
public class ExtensionMapperImpl implements ExtensionMapper {

    @Override
    public Extension extensionEntityToDomain(ExtensionEntity extensionEntity) {
        if(extensionEntity == null) {
            return null;
        }
        return Extension.builder()
                .id(extensionEntity.getId())
                .userId(extensionEntity.getUserId())
                .studyRoomId(extensionEntity.getStudyRoom().getId())
                .build();
    }

}
