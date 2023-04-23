package io.github.v1serviceapplication.domain.picnic.mapper;

import io.github.v1serviceapplication.domain.picnic.domain.PicnicEntity;
import io.github.v1serviceapplication.picnic.Picnic;
import org.springframework.stereotype.Component;

@Component
public class PicnicMapperImpl implements PicnicMapper{
    @Override
    public PicnicEntity picnicDomainToEntity(Picnic picnic) {

        return PicnicEntity.builder()
                .id(picnic.getId())
                .userId(picnic.getUserId())
                .dateTime(picnic.getDateTime())
                .startTime(picnic.getStartTime())
                .endTime(picnic.getEndTime())
                .reason(picnic.getReason())
                .arrangement(picnic.getArrangement())
                .dormitoryReturnCheckTime(picnic.getDormitoryReturnCheckTime())
                .isAcceptance(picnic.getIsAcceptance())
                .picnicRequestStartTime(picnic.getPicnicRequestStartTime())
                .picnicAllowStartTime(picnic.getPicnicAllowStartTime())
                .picnicRequestEndTime(picnic.getPicnicRequestEndTime())
                .build();
    }

    @Override
    public Picnic picnicEntityToDomain(PicnicEntity picnicEntity) {
        return Picnic.builder()
                .id(picnicEntity.getId())
                .userId(picnicEntity.getUserId())
                .dateTime(picnicEntity.getDateTime())
                .startTime(picnicEntity.getStartTime())
                .endTime(picnicEntity.getEndTime())
                .reason(picnicEntity.getReason())
                .arrangement(picnicEntity.getArrangement())
                .dormitoryReturnCheckTime(picnicEntity.getDormitoryReturnCheckTime())
                .isAcceptance(picnicEntity.getIsAcceptance())
                .picnicRequestStartTime(picnicEntity.getPicnicRequestStartTime())
                .picnicAllowStartTime(picnicEntity.getPicnicAllowStartTime())
                .picnicRequestEndTime(picnicEntity.getPicnicRequestEndTime())
                .build();
    }
}
