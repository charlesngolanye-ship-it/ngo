package org.charlesngolanye.ngo.services;

import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.requestDtos.ChangeUserPasswordRequest;
import org.charlesngolanye.ngo.dtos.requestDtos.RegisterUserRequestDto;
import org.charlesngolanye.ngo.dtos.requestDtos.UpdateUserRequest;
import org.charlesngolanye.ngo.dtos.responseDtos.UserResponseDto;
import org.charlesngolanye.ngo.entities.Role;
import org.charlesngolanye.ngo.entities.User;
import org.charlesngolanye.ngo.exceptions.UserNotFoundException;
import org.charlesngolanye.ngo.mappers.UserMapper;
import org.charlesngolanye.ngo.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto registerUser(RegisterUserRequestDto requestDto) {
        if (userRepository.existsByEmailIgnoreCase(requestDto.getEmail())) {
            throw new IllegalArgumentException("A user with this email already exists.");
        }

        User user = userMapper.toEntity(requestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.FINANCE_OFFICER);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto>getUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));
        return userMapper.toDto(user);
    }

    public UserResponseDto updateUser(Long id, UpdateUserRequest request) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User Not Found"));

        if (userRepository.existsByEmailIgnoreCaseAndIdNot(request.getEmail(), id)) {
            throw new IllegalArgumentException("Another user with this email already exists.");
        }

        userMapper.update(request, existingUser);
        User updatedUser = userRepository.save(existingUser);

        return userMapper.toDto(updatedUser);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));
        userRepository.delete(user);
    }

    public void changeUserPassword(Long id, ChangeUserPasswordRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        if (!user.getPassword().equals(request.getOldPassword())) {
            throw new IllegalArgumentException("Incorrect current password.");
        }

        user.setPassword(request.getNewPassword());
        userRepository.save(user);
    }
}
