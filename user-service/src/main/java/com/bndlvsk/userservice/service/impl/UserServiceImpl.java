package com.bndlvsk.userservice.service.impl;

import com.bndlvsk.userservice.dto.request.SignUpRequest;
import com.bndlvsk.userservice.dto.request.UserUpdateRequest;
import com.bndlvsk.userservice.dto.response.UserResponse;
import com.bndlvsk.userservice.exception.DuplicateFoundException;
import com.bndlvsk.userservice.exception.ResourceNotFoundException;
import com.bndlvsk.userservice.exception.ValidationException;
import com.bndlvsk.userservice.mapper.UserMapper;
import com.bndlvsk.userservice.model.Role;
import com.bndlvsk.userservice.model.RoleType;
import com.bndlvsk.userservice.model.User;
import com.bndlvsk.userservice.repository.RoleRepository;
import com.bndlvsk.userservice.repository.UserRepository;
import com.bndlvsk.userservice.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.bndlvsk.userservice.util.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public UserResponse createUser(SignUpRequest signUpRequest) {
        validatePassword(signUpRequest.password(), signUpRequest.confirmPassword());
        checkCreateUserData(signUpRequest);
        
        Role customerRole = roleRepository.findByName(RoleType.CUSTOMER)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(NOT_FOUND_MESSAGE, "Role", RoleType.CUSTOMER)));

        User user = userMapper.signUpRequestToEntity(signUpRequest);
        user.setPassword(passwordEncoder.encode(signUpRequest.password()));
        user.setRole(customerRole);
        
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateUser(Long id, UserUpdateRequest updateUserRequest) {
        User user = findUserByIdOrThrow(id);
        checkUpdateUserData(updateUserRequest, user);
        userMapper.updateUserFromUpdateRequest(updateUserRequest, user);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = findUserByIdOrThrow(id);
        return userMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .toList();
    }

    private void checkCreateUserData(SignUpRequest signUpRequest) {
        if (userRepository.existsByLogin(signUpRequest.login())) {
            throw new DuplicateFoundException(String.format(ALREADY_USED_MESSAGE, signUpRequest.login()));
        }
        if (userRepository.existsByEmail(signUpRequest.email())) {
            throw new DuplicateFoundException(String.format(ALREADY_USED_MESSAGE, signUpRequest.email()));
        }
        if (userRepository.existsByPhone(signUpRequest.phone())) {
            throw new DuplicateFoundException(String.format(ALREADY_USED_MESSAGE, signUpRequest.phone()));
        }
    }

    private void checkUpdateUserData(UserUpdateRequest updateUserRequest, User existingUser) {
        validatePassword(updateUserRequest.password(), updateUserRequest.confirmPassword());
        if (!updateUserRequest.login().equals(existingUser.getLogin()) && userRepository.existsByLogin(updateUserRequest.login())) {
            throw new DuplicateFoundException(String.format(ALREADY_USED_MESSAGE, updateUserRequest.login()));
        }
        if (!updateUserRequest.email().equals(existingUser.getEmail()) && userRepository.existsByEmail(updateUserRequest.email())) {
            throw new DuplicateFoundException(String.format(ALREADY_USED_MESSAGE, updateUserRequest.email()));
        }
        if (!updateUserRequest.phone().equals(existingUser.getPhone()) && userRepository.existsByPhone(updateUserRequest.phone())) {
            throw new DuplicateFoundException(String.format(ALREADY_USED_MESSAGE, updateUserRequest.phone()));
        }
    }

    private User findUserByIdOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format(NOT_FOUND_MESSAGE, "User", id))
                );
    }

    private void validatePassword(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new ValidationException(PASSWORDS_DO_NOT_MATCH);
        }
    }
}
