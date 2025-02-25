package com.bndlvsk.userservice.dto.response;

import com.bndlvsk.userservice.model.Gender;

import java.time.LocalDate;

public record UserResponse(
        Long id,
        String phone,
        String name,
        String login,
        String email,
        Gender gender,
        LocalDate dateOfBirth,
        String role
) {}
