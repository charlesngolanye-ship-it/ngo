package org.charlesngolanye.ngo.mappers;

import org.charlesngolanye.ngo.dtos.GrantDto;
import org.charlesngolanye.ngo.entities.Grant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GrantMapper {
    GrantDto toDto(Grant grant);
}
