package io.github.v1serviceapplication.domain.picnicdatetime.domain.mapper;

import io.github.v1serviceapplication.domain.picnicdatetime.domain.PicnicTimeEntity;
import io.github.v1serviceapplication.picnicdatetime.PicnicTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PicnicTimeMapperImpl implements PicnicTimeMapper {
    @Override
    public PicnicTimeEntity picnicTimeDomainToEntity(PicnicTime picnicDateTime) {
        return PicnicTimeEntity.builder()
                .id(picnicDateTime.getId())
                .timeType(picnicDateTime.getTimeType())
                .picnicTime(picnicDateTime.getPicnicTime())
                .day(picnicDateTime.getDay())
                .build();
    }

    @Override
    public PicnicTime picnicTimeEntityToDomain(PicnicTimeEntity picnicDateTimeEntity) {
        return PicnicTime.builder()
                .id(picnicDateTimeEntity.getId())
                .timeType(picnicDateTimeEntity.getTimeType())
                .picnicTime(picnicDateTimeEntity.getPicnicTime())
                .day(picnicDateTimeEntity.getDay())
                .build();
    }
}
