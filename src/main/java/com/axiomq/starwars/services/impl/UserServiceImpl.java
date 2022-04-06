package com.axiomq.starwars.services.impl;

import com.axiomq.starwars.entities.User;
import com.axiomq.starwars.enums.Role;
import com.axiomq.starwars.exceptions.ObjectNotFoundException;
import com.axiomq.starwars.repositories.UserRepository;
import com.axiomq.starwars.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("User not found: %d", id)));
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

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow( () ->
                new ObjectNotFoundException(String.format("User with email %s does not exist.", email))
        );
    }

}
