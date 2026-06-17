package org.charlesngolanye.ngo.mappers;

import org.charlesngolanye.ngo.dtos.GrantRequestDto;
import org.charlesngolanye.ngo.dtos.GrantResponseDto;
import org.charlesngolanye.ngo.entities.Grant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GrantMapper {
    // Converts an incoming request payload into a DB Entity
    Grant toEntity(GrantRequestDto request);

    // Converts a DB Entity into an outgoing response payload
    GrantResponseDto toDto(Grant grant);
}
