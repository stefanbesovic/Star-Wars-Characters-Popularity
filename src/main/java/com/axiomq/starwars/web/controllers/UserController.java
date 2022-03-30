package com.axiomq.starwars.web.controllers;

import com.axiomq.starwars.entities.User;
import com.axiomq.starwars.services.UserService;
import com.axiomq.starwars.web.dtos.user.UserMapper;
import com.axiomq.starwars.web.dtos.user.UserRequest;
import com.axiomq.starwars.web.dtos.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponse saveUser(@Valid @RequestBody UserRequest userRequest) {
        User user = userService.saveUser(UserMapper.INSTANCE.fromRequest(userRequest));
        return UserMapper.INSTANCE.toResponse(user);
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(UserMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable("id") Long id) {
        return UserMapper.INSTANCE.toResponse(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable("id") Long id,
                                   @Valid @RequestBody UserRequest userRequest) {
        User user = userService.updateUser(UserMapper.INSTANCE.fromRequest(userRequest), id);
        return UserMapper.INSTANCE.toResponse(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }
}
