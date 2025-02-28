package com.bndlvsk.userservice.mapper;

import com.bndlvsk.userservice.dto.request.UserCreateRequest;
import com.bndlvsk.userservice.dto.request.UserUpdateRequest;
import com.bndlvsk.userservice.dto.response.UserResponse;
import com.bndlvsk.userservice.model.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", ignore = true)
    User createRequestToEntity(UserCreateRequest userCreateRequest);

    void updateUserFromUpdateRequest(UserUpdateRequest userUpdateRequest, @MappingTarget User user);

    @Mapping(source = "role.name", target = "role")
    UserResponse toResponse(User user);
}