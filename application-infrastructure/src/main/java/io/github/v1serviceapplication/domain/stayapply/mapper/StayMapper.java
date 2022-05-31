package io.github.v1serviceapplication.domain.stayapply.mapper;

import io.github.v1serviceapplication.domain.stayapply.domain.StayEntity;
import io.github.v1serviceapplication.stayapply.Stay;
import org.mapstruct.Mapper;

@Mapper
public interface StayMapper {
    StayEntity stayDomainToEntity(Stay stay);

    Stay stayEntityToDomain(StayEntity stayEntity);
}
