package com.bndlvsk.userservice.dto.response;

public record AuthenticationResponse(
    String token,
    String type
) {} 