package com.bndlvsk.userservice.controller;

import com.bndlvsk.userservice.config.JwtProperties;
import com.bndlvsk.userservice.dto.request.AuthenticationRequest;
import com.bndlvsk.userservice.dto.request.SignUpRequest;
import com.bndlvsk.userservice.dto.response.AuthenticationResponse;
import com.bndlvsk.userservice.dto.response.UserResponse;
import com.bndlvsk.userservice.security.JwtUtil;
import com.bndlvsk.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final JwtProperties jwtProperties;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signUp(
            @RequestBody @Valid SignUpRequest request
    ) {
        UserResponse newUser = userService.createUser(request);
        
        final UserDetails userDetails = userDetailsService.loadUserByUsername(newUser.login());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt, jwtProperties.getTokenPrefix()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest request
    ) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.login(), request.password())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.login());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt, jwtProperties.getTokenPrefix()));
    }
} 