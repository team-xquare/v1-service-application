package io.github.v1serviceapplication.domain.studyroom.mapper;

import io.github.v1serviceapplication.domain.studyroom.domain.StudyRoomEntity;
import io.github.v1serviceapplication.studyroom.StudyRoom;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudyRoomMapper {
    StudyRoom studyRoomEntityToDomain(StudyRoomEntity studyRoom);

    StudyRoomEntity studyRoomDomainToEntity(StudyRoom studyRoom);
}
