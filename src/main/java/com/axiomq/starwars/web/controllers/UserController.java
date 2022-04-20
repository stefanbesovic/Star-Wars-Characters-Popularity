package com.axiomq.starwars.web.controllers;

import com.axiomq.starwars.entities.User;
import com.axiomq.starwars.services.UserService;
import com.axiomq.starwars.web.dtos.user.UserMapper;
import com.axiomq.starwars.web.dtos.user.UserRequest;
import com.axiomq.starwars.web.dtos.user.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
@Tag(name = "User Controller", description = "Set of endpoints for Creating, Retrieving, Updating and Deleting of User.")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Creates new User")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User"),
            @ApiResponse(responseCode = "400", description = "Validation error : invalid argument"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping
    public UserResponse saveUser(@Valid @RequestBody UserRequest userRequest) {
        User user = userService.saveUser(UserMapper.INSTANCE.fromRequest(userRequest));
        return UserMapper.INSTANCE.toResponse(user);
    }

    @Operation(summary = "Retrieves list of all Users")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of Users"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(UserMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Retrieves details about User")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User"),
            @ApiResponse(responseCode = "400", description = "Validation error : invalid argument"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable("id") Long id) {
        return UserMapper.INSTANCE.toResponse(userService.getUserById(id));
    }

    @Operation(summary = "Updates existing User")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User"),
            @ApiResponse(responseCode = "400", description = "Validation error : invalid argument"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable("id") Long id,
                                   @Valid @RequestBody UserRequest userRequest) {
        User user = userService.updateUser(UserMapper.INSTANCE.fromRequest(userRequest), id);
        return UserMapper.INSTANCE.toResponse(user);
    }

    @Operation(summary = "Deletes User")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User deleted"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }
}
