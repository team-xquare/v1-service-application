package io.github.v1serviceapplication.domain.picnicdatetime.domain.mapper;

import io.github.v1serviceapplication.domain.picnicdatetime.domain.PicnicDateTimeEntity;
import io.github.v1serviceapplication.picnicdatetime.PicnicDateTime;

public class PicnicDateTimeMapperImpl implements PicnicDateTimeMapper{
    @Override
    public PicnicDateTimeEntity picnicDateTimeDomainToEntity(PicnicDateTime picnicDateTime) {
        return PicnicDateTimeEntity.builder()
                .id(picnicDateTime.getId())
                .dateTimeType(picnicDateTime.getDateTimeType())
                .picnicDateTime(picnicDateTime.getPicnicDateTime())
                .build();
    }

    @Override
    public PicnicDateTime picnicEntityToDomain(PicnicDateTimeEntity picnicDateTimeEntity) {
        return PicnicDateTime.builder()
                .id(picnicDateTimeEntity.getId())
                .dateTimeType(picnicDateTimeEntity.getDateTimeType())
                .picnicDateTime(picnicDateTimeEntity.getPicnicDateTime())
                .build();
    }
}
