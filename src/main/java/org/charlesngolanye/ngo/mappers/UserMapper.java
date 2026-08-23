package org.charlesngolanye.ngo.mappers;


import org.charlesngolanye.ngo.dtos.requestDtos.RegisterUserRequestDto;
import org.charlesngolanye.ngo.dtos.requestDtos.UpdateUserRequest;
import org.charlesngolanye.ngo.dtos.responseDtos.UserResponseDto;
import org.charlesngolanye.ngo.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface UserMapper {
    // Converts an incoming request payload into a DB Entity
    User toEntity(RegisterUserRequestDto request);

    // Converts a DB Entity into an outgoing response payload
    UserResponseDto toDto(User user);

    void update(UpdateUserRequest request, @MappingTarget User user);
}
