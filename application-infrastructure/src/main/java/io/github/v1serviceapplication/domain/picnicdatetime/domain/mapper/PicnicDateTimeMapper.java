package io.github.v1serviceapplication.domain.picnicdatetime.domain.mapper;

import io.github.v1serviceapplication.domain.picnicdatetime.domain.PicnicDateTimeEntity;
import io.github.v1serviceapplication.picnicdatetime.PicnicDateTime;

public interface PicnicDateTimeMapper {

    PicnicDateTimeEntity picnicDateTimeDomainToEntity(PicnicDateTime picnicDateTime);

    PicnicDateTime picnicEntityToDomain(PicnicDateTimeEntity picnicDateTimeEntity);
}
