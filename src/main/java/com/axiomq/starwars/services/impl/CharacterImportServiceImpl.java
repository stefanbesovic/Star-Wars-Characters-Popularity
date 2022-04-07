package com.axiomq.starwars.services.impl;

import com.axiomq.starwars.entities.Character;
import com.axiomq.starwars.services.CharacterImportService;
import com.axiomq.starwars.web.dtos.character.CharacterGet;
import com.axiomq.starwars.web.dtos.character.CharacterConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Set;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CharacterImportServiceImpl implements CharacterImportService {

    private final CharacterConverter characterConverter;
    private final RestTemplate restTemplate;

    @Override
    public Set<Character> populateCharacters() {
        String url = "https://swapi.dev/api/people";
        try {
            CharacterGet response = restTemplate.getForObject(url, CharacterGet.class);
            return extractCharacters(response);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }

    }

    public Set<Character> extractCharacters(CharacterGet response) {
        return response.getResults().stream()
                .map(characterConverter::toCharacter)
                .collect(Collectors.toSet());

    }
}
