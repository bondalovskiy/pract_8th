package com.bndlvsk.orderservice.dto.external;

import java.time.LocalDate;

public record UserDto(
        Long id,
        String phone,
        String name,
        String login,
        String email,
        String gender,
        LocalDate dateOfBirth,
        String role
) {
}
