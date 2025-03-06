package com.bndlvsk.userservice.service;

import com.bndlvsk.userservice.dto.request.UserCreateRequest;
import com.bndlvsk.userservice.dto.request.UserUpdateRequest;
import com.bndlvsk.userservice.dto.request.SignUpRequest;
import com.bndlvsk.userservice.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(SignUpRequest signUpRequest);

    UserResponse updateUser(Long userId, UserUpdateRequest updateUserRequest);

    void deleteUser(Long id);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers();
}
