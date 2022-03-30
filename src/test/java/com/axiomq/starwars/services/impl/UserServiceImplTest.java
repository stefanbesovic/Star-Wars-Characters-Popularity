package com.axiomq.starwars.services.impl;

import com.axiomq.starwars.entities.User;
import com.axiomq.starwars.enums.Role;
import com.axiomq.starwars.repositories.UserRepository;
import com.axiomq.starwars.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void givenUser_whenGetUserById_thenOk() {
        //given
        User user = User.builder()
                .username("stefan")
                .email("stefanbesovic@gmail.com")
                .password("stefan123")
                .role(Role.USER)
                .build();

        //when
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        User actual = userService.getUserById(user.getId());

        //then
        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    void givenNonExistingUser_whenGetUserById_thenThrowNoSuchElementException() {
        //given
        User user = new User();

        //when
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
        Exception e = assertThrows(NoSuchElementException.class, () -> {
            userService.getUserById(user.getId());
        });

        //then
        assertTrue(e.getMessage().contains(String.format("User not found: %d", user.getId())));
    }

    @Test
    void givenUpdatedUser_whenUpdateUser_ThenOk() {
        //given
        User user = User.builder()
                .username("stefan")
                .email("stefanbesovic@gmail.com")
                .password("stefan123")
                .role(Role.USER)
                .build();

        //when
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(User.builder()
                .username("stefi")
                .email("stefanbesovic@gmail.com")
                .password("stefan13")
                .role(Role.USER)
                .build());
        User actual = userService.updateUser(user, user.getId());

        //then
        assertNotNull(actual);
        assertEquals("stefi", actual.getUsername());
        assertEquals("stefanbesovic@gmail.com", actual.getEmail());
        assertEquals("stefan13", actual.getPassword());
        assertEquals(Role.USER, actual.getRole());
    }
}