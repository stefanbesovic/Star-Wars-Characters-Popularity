package com.axiomq.starwars.services.impl;

import com.axiomq.starwars.entities.Character;
import com.axiomq.starwars.enums.Gender;
import com.axiomq.starwars.enums.Planet;
import com.axiomq.starwars.exceptions.ObjectNotFoundException;
import com.axiomq.starwars.repositories.CharacterRepository;
import com.axiomq.starwars.services.CharacterImportService;
import com.axiomq.starwars.services.CharacterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CharacterServiceImplTest {

    @Mock
    private CharacterRepository characterRepository;

    @Mock
    private CharacterImportService characterImportService;

    private CharacterService characterService;

    @BeforeEach
    void setUp() {
        characterService = new CharacterServiceImpl(characterRepository, characterImportService);
    }

    @Test
    void givenCharacter_whenGetCharacterById_thenOk() {
        //given
        Character character = Character.builder()
                .name("stefan")
                .gender(Gender.FEMALE)
                .planet(Planet.ALDERAAN)
                .votersCount(10)
                .build();

        //when
        when(characterRepository.findById(character.getId())).thenReturn(Optional.of(character));
        Character actual = characterService.getCharacterById(character.getId());

        //then
        verify(characterRepository, times(1)).findById(character.getId());
    }

    @Test
    void givenNonExistingCharacter_whenGetCharacterById_thenThrowNoSuchElementException() {
        //given
        Character character = new Character();

        //when
        when(characterRepository.findById(character.getId())).thenReturn(Optional.empty());
        Exception e = assertThrows(ObjectNotFoundException.class, () -> {
            characterService.getCharacterById(character.getId());
        });

        //then
        assertTrue(e.getMessage().contains(String.format("Character not found: %d", character.getId())));
    }

    @Test
    void givenUpdatedCharacter_whenUpdateCharacter_ThenOk() {
        //given
        Character character = Character.builder()
                .name("stefan")
                .gender(Gender.FEMALE)
                .planet(Planet.ALDERAAN)
                .votersCount(10)
                .build();

        //when
        when(characterRepository.findById(character.getId())).thenReturn(Optional.of(character));
        when(characterRepository.save(character)).thenReturn(Character.builder()
                .name("stefi")
                .gender(Gender.MALE)
                .planet(Planet.BESPIN)
                .votersCount(2)
                .build()
        );

        Character actual = characterService.updateCharacter(character, character.getId());

        //then
        assertNotNull(actual);
        assertEquals("stefi", actual.getName());
        assertEquals(Gender.MALE, actual.getGender());
        assertEquals(Planet.BESPIN, actual.getPlanet());
        assertEquals(2, actual.getVotersCount());
    }
}
