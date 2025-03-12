package com.bndlvsk.userservice.controller;

import com.bndlvsk.userservice.dto.request.SignUpRequest;
import com.bndlvsk.userservice.dto.request.UserUpdateRequest;
import com.bndlvsk.userservice.dto.response.UserResponse;
import com.bndlvsk.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponse createUser(@RequestBody @Valid SignUpRequest signUpRequest) {
        return userService.createUser(signUpRequest);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(
            @PathVariable Long id,
            @RequestBody @Valid UserUpdateRequest userUpdateRequest
    ) {
        return userService.updateUser(id, userUpdateRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }
}