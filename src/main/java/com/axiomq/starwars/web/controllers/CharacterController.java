package com.axiomq.starwars.web.controllers;

import com.axiomq.starwars.entities.Character;
import com.axiomq.starwars.services.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/character")
public class CharacterController {

    private final CharacterService characterService;

    @GetMapping
    public List<Character> getAllCharacters() {
        return characterService.getAllCharacters();
    }

    @DeleteMapping("/{id}")
    public void deleteCharacter(@PathVariable("id") Long id) {
        characterService.deleteCharacter(id);
    }

}
