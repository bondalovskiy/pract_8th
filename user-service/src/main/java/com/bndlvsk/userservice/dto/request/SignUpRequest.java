package com.bndlvsk.userservice.dto.request;

import com.bndlvsk.userservice.model.Gender;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record SignUpRequest(
    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "\\+?[0-9]{7,15}", message = "Invalid phone number format")
    String phone,

    @NotBlank(message = "Name is required")
    String name,

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    String password,

    @NotBlank(message = "Password confirmation is required")
    String confirmPassword,

    @NotBlank(message = "Login is required")
    String login,

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    String email,

    @NotNull(message = "Gender is required")
    Gender gender,

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    LocalDate dateOfBirth
) {} 