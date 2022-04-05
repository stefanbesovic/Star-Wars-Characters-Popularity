package com.axiomq.starwars.services.impl;

import com.axiomq.starwars.entities.Character;
import com.axiomq.starwars.repositories.CharacterRepository;
import com.axiomq.starwars.services.CharacterImportService;
import com.axiomq.starwars.services.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

import java.util.*;

@RequiredArgsConstructor
@Service
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;
    private final CharacterImportService characterImportService;

    @Override
    public List<Character> getAllCharacters() {
        return characterRepository.findAll();
    }

    @Override
    public Character getCharacterById(Long id) {
        return characterRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("Character not found: %d", id)));
    }

    @Override
    public Character updateCharacter(Character character, Long id) {
        Character existing = getCharacterById(id);

        existing.setName(character.getName());
        existing.setFilms(character.getFilms());
        existing.setGender(character.getGender());
        existing.setPlanet(character.getPlanet());
        existing.setVotersCount(character.getVotersCount());

        return characterRepository.save(existing);
    }

    @Override
    public void deleteCharacter(Long id) {
        Character character = getCharacterById(id);
        characterRepository.deleteById(character.getId());
    }

    @Override
    public void populateCharacters() {
        Set<Character> characters = characterImportService.populateCharacters();
        characterRepository.saveAll(characters);
    }

}
