package io.github.v1serviceapplication.domain.picnic.mapper;

import io.github.v1serviceapplication.domain.picnic.domain.PicnicEntity;
import io.github.v1serviceapplication.picnic.Picnic;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PicnicMapper {
    PicnicEntity picnicDomainToEntity(Picnic picnic);

    Picnic picnicEntityToDomain(PicnicEntity picnicEntity);
}