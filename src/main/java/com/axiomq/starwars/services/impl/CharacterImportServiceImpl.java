package com.axiomq.starwars.services.impl;

import com.axiomq.starwars.config.AppProperties;
import com.axiomq.starwars.entities.Character;
import com.axiomq.starwars.entities.Film;
import com.axiomq.starwars.services.CharacterImportService;
import com.axiomq.starwars.services.FilmService;
import com.axiomq.starwars.web.dtos.character.CharacterGet;
import com.axiomq.starwars.web.dtos.character.CharacterConverter;
import com.axiomq.starwars.web.dtos.character.CharacterResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CharacterImportServiceImpl implements CharacterImportService {

    private final CharacterConverter characterConverter;
    private final FilmService filmService;
    private final RestTemplate restTemplate;
    private final AppProperties appProperties;

    @Override
    public Set<Character> populateCharacters() {
        String url = appProperties.getUrl();
        List<Film> films = filmService.getAllFilms();
        try {
            CharacterGet response = restTemplate.getForObject(url, CharacterGet.class);
            return extractCharacters(response, films);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }

    }

    public Set<Character> extractCharacters(CharacterGet response, List<Film> films) {
        return response.getResults().stream()
                .map((CharacterResponse characterResponse) -> characterConverter.toCharacter(characterResponse, films))
                .collect(Collectors.toSet());

    }
}
