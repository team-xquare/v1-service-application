package io.github.v1serviceapplication.domain.stay.mapper;

import io.github.v1serviceapplication.domain.stay.domain.StayEntity;
import io.github.v1serviceapplication.stay.Stay;
import org.mapstruct.Mapper;

@Mapper
public interface StayMapper {
    StayEntity stayDomainToEntity(Stay stay);

    Stay stayEntityToDomain(StayEntity stayEntity);
}
