package com.axiomq.starwars.web.controllers;

import com.axiomq.starwars.services.UserService;
import com.axiomq.starwars.web.dtos.user.UserMapper;
import com.axiomq.starwars.web.dtos.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin/user")
public class UserViewController {

    private final UserService userService;

    @GetMapping()
    public ModelAndView users(Model model, Principal principal) {
        List<UserResponse> users = userService.getAllUsers()
                .stream()
                .map(UserMapper.INSTANCE::toResponse)
                .filter(userResponse -> !Objects.equals(userResponse.getEmail(), principal.getName()))
                .collect(Collectors.toList());

        model.addAttribute("users", users);

        return new ModelAndView("users");
    }

    @GetMapping("/delete")
    public ModelAndView deleteUser(@RequestParam("userId") Long id) {

        userService.deleteUser(id);

        return new ModelAndView("redirect:/admin/user");
    }
}
