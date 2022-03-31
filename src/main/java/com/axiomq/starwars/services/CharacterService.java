package com.axiomq.starwars.services;

import com.axiomq.starwars.entities.Character;

import java.util.List;

public interface CharacterService {
    List<Character> getAllCharacters();
    Character getCharacterById(Long id);
    Character updateCharacter(Character character, Long id);
    void deleteCharacter(Long id);
    void populateCharacters();
}
