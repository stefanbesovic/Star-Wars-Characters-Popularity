package com.axiomq.starwars.services.impl;

import com.axiomq.starwars.entities.User;
import com.axiomq.starwars.enums.Role;
import com.axiomq.starwars.repositories.UserRepository;
import com.axiomq.starwars.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("User not found: %d", id)));
    }

    @Override
    public User updateUser(User user, Long id) {
        User existing = getUserById(id);
        existing.setUsername(user.getUsername());
        existing.setEmail(user.getEmail());
        existing.setPassword(user.getPassword());
        existing.setRole(Role.USER);

        return userRepository.save(existing);
    }

    @Override
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.deleteById(user.getId());
    }
}