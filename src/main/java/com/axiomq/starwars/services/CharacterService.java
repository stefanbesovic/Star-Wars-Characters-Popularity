package com.axiomq.starwars.services;

import com.axiomq.starwars.entities.Character;

import java.security.Principal;
import java.util.List;

public interface CharacterService {
    Character saveCharacter(Character character);
    List<Character> getAllCharacters();
    Character getCharacterById(Long id);
    Character updateCharacter(Character character, Long id);
    void deleteCharacter(Long id);
    void populateCharacters();
    void updateCharacterVotersCount(Long characterId, Principal principal);
}
