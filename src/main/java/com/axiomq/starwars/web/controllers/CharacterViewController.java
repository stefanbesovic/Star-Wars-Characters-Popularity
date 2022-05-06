package com.axiomq.starwars.web.controllers;

import com.axiomq.starwars.services.CharacterService;
import com.axiomq.starwars.web.dtos.character.CharacterDto;
import com.axiomq.starwars.web.dtos.character.CharacterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/character")
public class CharacterViewController {

    private final CharacterService characterService;

    @GetMapping("/all")
    public ModelAndView characters(Model model) {
        List<CharacterDto> characters = characterService.getAllCharacters()
                .stream()
                .map(CharacterMapper.INSTANCE::toDto)
                .collect(Collectors.toList());

        model.addAttribute("characters", characters);

        return new ModelAndView("characters");
    }

    @GetMapping("/delete")
    public ModelAndView deleteCharacter(@RequestParam("characterId") Long id) {

        characterService.deleteCharacter(id);

        return new ModelAndView("redirect:/api/character/all");
    }
}
