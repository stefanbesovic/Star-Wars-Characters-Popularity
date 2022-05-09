package com.axiomq.starwars.web.controllers;

import com.axiomq.starwars.services.UserService;
import com.axiomq.starwars.web.dtos.user.UserMapper;
import com.axiomq.starwars.web.dtos.user.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class LoginController {

    private final UserService userService;

    @GetMapping
    public ModelAndView home() {
        return new ModelAndView("welcome");
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping("/register")
    public ModelAndView register(Model model) {
        UserRequest userRequest = new UserRequest();

        model.addAttribute("user", userRequest);

        return new ModelAndView("register");
    }

    @PostMapping("/register")
    public ModelAndView saveUser(@Valid @ModelAttribute("user") UserRequest userRequest) {
        userService.saveUser(UserMapper.INSTANCE.fromRequest(userRequest));
        return new ModelAndView("register_hi");
    }
}
