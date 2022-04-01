package com.axiomq.starwars.services.impl;

import com.axiomq.starwars.entities.Character;
import com.axiomq.starwars.repositories.CharacterRepository;
import com.axiomq.starwars.services.CharacterService;
import com.axiomq.starwars.services.FilmService;
import com.axiomq.starwars.web.dtos.character.CharacterGet;
import com.axiomq.starwars.web.dtos.character.CharacterResponse;
import com.axiomq.starwars.web.dtos.character.ConvertCharacter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import javax.sql.DataSource;
import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;
    private final FilmService filmService;

    @Autowired
    private DataSource dataSource;

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
        WebClient webClient = WebClient.create("https://swapi.dev");

        Flux<CharacterGet> response = getResponse(webClient);

        Set<Character> characters = extractCharacters(response);

        characters.forEach(character -> characterRepository.save(character));
    }

    private Set<Character> extractCharacters(Flux<CharacterGet> response) {
        ConvertCharacter convertCharacter = new ConvertCharacter(filmService);
        Set<Character> real = new HashSet<>();
        response.toStream()
                .forEach(characterGet -> {
                    List<CharacterResponse> characters = new ArrayList<>(characterGet.getResults());
                    characters.forEach(characterResponse ->
                            real.add(convertCharacter.toCharacter(characterResponse)));
                });

        return real;
    }

    public Flux<CharacterGet> getResponse(WebClient webClient) {

        return webClient.get()
                .uri("/api/people")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(CharacterGet.class);
    }

    @Override
    public void addCharacterVotersCount(Long characterId, Principal principal) {
        Character character = getCharacterById(characterId);
        character.addEmail(principal.getName());
        character.setVotersCount(character.getUsersEmail().size());
        characterRepository.save(character);
    }

    @Override
    public void removeCharacterVotersCount(Long characterId, Principal principal) {
        Character character = getCharacterById(characterId);
        character.removeEmail(principal.getName());
        character.setVotersCount(character.getUsersEmail().size());
        characterRepository.save(character);
    }
}
