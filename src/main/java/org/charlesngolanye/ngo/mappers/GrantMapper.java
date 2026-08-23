package org.charlesngolanye.ngo.mappers;

import org.charlesngolanye.ngo.dtos.requestDtos.GrantRequestDto;
import org.charlesngolanye.ngo.dtos.responseDtos.GrantResponseDto;
import org.charlesngolanye.ngo.dtos.requestDtos.UpdateGrantRequest;
import org.charlesngolanye.ngo.entities.Grant;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring" , unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface GrantMapper {
    // Converts an incoming request payload into a DB Entity
    Grant toEntity(GrantRequestDto request);

    // Converts a DB Entity into an outgoing response payload
    GrantResponseDto toDto(Grant grant);

    void update(UpdateGrantRequest request, @MappingTarget Grant grant);

}

