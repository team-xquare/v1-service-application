package io.github.v1serviceapplication.domain.picnic.mapper;

import io.github.v1serviceapplication.domain.picnic.domain.PicnicEntity;
import io.github.v1serviceapplication.picnic.Picnic;
import org.springframework.stereotype.Component;

@Component
public class PicnicMapperImpl implements PicnicMapper {
    @Override
    public PicnicEntity picnicDomainToEntity(Picnic picnic) {

        return PicnicEntity.builder()
                .id(picnic.getId())
                .userId(picnic.getUserId())
                .createDateTime(picnic.getCreateDateTime())
                .startTime(picnic.getStartTime())
                .endTime(picnic.getEndTime())
                .reason(picnic.getReason())
                .arrangement(picnic.getArrangement())
                .build();
    }

    @Override
    public Picnic picnicEntityToDomain(PicnicEntity picnicEntity) {
        return Picnic.builder()
                .id(picnicEntity.getId())
                .userId(picnicEntity.getUserId())
                .createDateTime(picnicEntity.getCreateDateTime())
                .startTime(picnicEntity.getStartTime())
                .endTime(picnicEntity.getEndTime())
                .reason(picnicEntity.getReason())
                .arrangement(picnicEntity.getArrangement())
                .build();
    }
}
