package com.axiomq.starwars.web.controllers;

import com.axiomq.starwars.services.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final CharacterService characterService;

    @PostMapping("/script")
    public void populateCharacters() {
        characterService.populateCharacters();
    }
}
