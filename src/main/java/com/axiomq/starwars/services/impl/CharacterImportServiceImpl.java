package com.axiomq.starwars.services.impl;

import com.axiomq.starwars.entities.Character;
import com.axiomq.starwars.services.CharacterImportService;
import com.axiomq.starwars.web.dtos.character.CharacterGet;
import com.axiomq.starwars.web.dtos.character.CharacterResponse;
import com.axiomq.starwars.web.dtos.character.ConvertCharacter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class CharacterImportServiceImpl implements CharacterImportService {

    private final ConvertCharacter convertCharacter;

    @Override
    public Set<Character> populateCharacters() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://swapi.dev/api/people";
        CharacterGet response = restTemplate.getForObject(url, CharacterGet.class);

        if(response == null)
            return new HashSet<>();

        return extractCharacters(response);
    }

    public Set<Character> extractCharacters(CharacterGet response) {
        Set<Character> real = new HashSet<>();

        List<CharacterResponse> characters = new ArrayList<>(response.getResults());
        characters.forEach(characterResponse ->
                real.add(convertCharacter.toCharacter(characterResponse)));

        return real;
    }
}
