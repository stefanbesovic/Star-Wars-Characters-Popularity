package com.axiomq.starwars.services.impl;

import com.axiomq.starwars.entities.Character;
import com.axiomq.starwars.exceptions.ObjectNotFoundException;
import com.axiomq.starwars.repositories.CharacterRepository;
import com.axiomq.starwars.services.CharacterImportService;
import com.axiomq.starwars.services.CharacterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;
    private final CharacterImportService characterImportService;

    @Override
    public List<Character> getAllCharacters() {
        log.info("Getting list of all characters.");
        return characterRepository.findAll();
    }

    @Override
    public Character getCharacterById(Long id) {
        log.info("Getting character by id: {}", id);
        return characterRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Character not found: %d", id)));
    }

    @Override
    public Character updateCharacter(Character character, Long id) {
        log.info("Updating character with id: {}.", id);

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
        log.info("Deleting character with id {}", id);
        Character character = getCharacterById(id);
        characterRepository.deleteById(character.getId());
    }

    @Override
    public void populateCharacters() {
        Set<Character> characters = characterImportService.populateCharacters();
        log.info("Saving all fetched Characters from SWAPI to database.");
        characterRepository.saveAll(characters);
    }

}
