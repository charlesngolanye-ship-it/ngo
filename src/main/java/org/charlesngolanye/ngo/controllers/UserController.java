package org.charlesngolanye.ngo.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.requestDtos.ChangeUserPasswordRequest;
import org.charlesngolanye.ngo.dtos.requestDtos.RegisterUserRequestDto;
import org.charlesngolanye.ngo.dtos.requestDtos.UpdateUserRequest;
import org.charlesngolanye.ngo.dtos.responseDtos.UserResponseDto;
import org.charlesngolanye.ngo.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "Users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(
            @Valid @RequestBody RegisterUserRequestDto requestDto,
            UriComponentsBuilder uriBuilder) {

        UserResponseDto responseDto = userService.createUser(requestDto);

        var uri = uriBuilder.path("/users/{id}").buildAndExpand(responseDto.getId()).toUri();

        return ResponseEntity.created(uri).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable(name = "id") Long id,
            @RequestBody UpdateUserRequest request) {
        UserResponseDto updated = userService.updateUser(id, request);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(
            @PathVariable Long id,
            @Valid @RequestBody ChangeUserPasswordRequest request) {
        userService.changeUserPassword(id, request);

        return ResponseEntity.ok().build();
    }
}
