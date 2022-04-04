package com.axiomq.starwars.services;

import com.axiomq.starwars.entities.Character;

import java.util.Set;

public interface CharacterImportService {
    Set<Character> populateCharacters();
}
