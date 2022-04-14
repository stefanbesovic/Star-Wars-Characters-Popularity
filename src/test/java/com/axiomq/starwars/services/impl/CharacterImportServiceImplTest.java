package com.axiomq.starwars.services.impl;


import com.axiomq.starwars.entities.Character;
import com.axiomq.starwars.services.CharacterImportService;
import com.axiomq.starwars.web.dtos.character.CharacterGet;
import com.axiomq.starwars.web.dtos.character.CharacterResponse;
import com.axiomq.starwars.web.dtos.character.CharacterConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CharacterImportServiceImplTest {

    @Mock
    private CharacterConverter characterConverter;

    @Mock
    private RestTemplate restTemplate;

    private CharacterImportService characterImportService;

    @BeforeEach
    void setUp() {
        characterImportService = new CharacterImportServiceImpl(characterConverter, restTemplate);
    }

    @Test
    void givenResponse_whenPopulateCharacters_thenReturnOk() {

        //given
        CharacterResponse characterResponse = CharacterResponse.builder()
                .name("Luke")
                .gender("MALE")
                .homeworld("1")
                .films(Set.of("1", "2"))
                .build();

        CharacterGet response = CharacterGet.builder()
                .next("https://swapi.dev/api/people/?page=2")
                .results(List.of(characterResponse))
                .build();

        //when
        Mockito.when(restTemplate.getForObject(anyString(), eq(CharacterGet.class))).thenReturn(response);
        Set<Character> characters = characterImportService.populateCharacters();

        //then
        verify(characterConverter, times(1)).toCharacter(any());
    }

    @Test
    void givenNullResponse_whenPopulateCharacters_thenReturnEmptySet() {

        //given
        CharacterGet response = any();

        //when
        Mockito.when(restTemplate.getForObject(anyString(), eq(CharacterGet.class))).thenReturn(response);
        Exception e = assertThrows(HttpClientErrorException.class, () -> {
            characterImportService.populateCharacters();
        });

    }
}