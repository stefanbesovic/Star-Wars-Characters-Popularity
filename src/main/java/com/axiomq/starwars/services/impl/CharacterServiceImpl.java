package com.axiomq.starwars.services.impl;

import com.axiomq.starwars.entities.Character;
import com.axiomq.starwars.repositories.CharacterRepository;
import com.axiomq.starwars.services.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;

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
        Resource resource = new ClassPathResource("characters.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.execute(dataSource);
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
