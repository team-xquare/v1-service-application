package io.github.v1serviceapplication.domain.picnicdatetime.domain.mapper;

import io.github.v1serviceapplication.domain.picnicdatetime.domain.PicnicTimeEntity;
import io.github.v1serviceapplication.picnicdatetime.PicnicTime;

public interface PicnicTimeMapper {

    PicnicTimeEntity picnicTimeDomainToEntity(PicnicTime picnicTime);

    PicnicTime picnicTimeEntityToDomain(PicnicTimeEntity picnicTimeEntity);
}
