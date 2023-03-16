package io.github.v1serviceapplication.domain.stay.mapper;

import io.github.v1serviceapplication.domain.stay.domain.StayEntity;
import io.github.v1serviceapplication.stay.api.dto.response.StayStatus;

// @Mapper(componentModel = "spring")
public interface StayMapper {
    /*StayEntity stayDomainToEntity(Stay stay);

    Stay stayEntityToDomain(StayEntity stayEntity);*/

    StayStatus mapToStayStatus(StayEntity stayStatus);
}
