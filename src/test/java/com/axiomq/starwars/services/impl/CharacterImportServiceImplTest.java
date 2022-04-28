package com.axiomq.starwars.services.impl;


import com.axiomq.starwars.config.AppProperties;
import com.axiomq.starwars.services.CharacterImportService;
import com.axiomq.starwars.services.FilmService;
import com.axiomq.starwars.web.dtos.character.CharacterGet;
import com.axiomq.starwars.web.dtos.character.CharacterResponse;
import com.axiomq.starwars.util.CharacterConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@EnableConfigurationProperties(value = AppProperties.class)
@TestPropertySource("classpath:application.properties")
class CharacterImportServiceImplTest {

    @Mock
    private CharacterConverter characterConverter;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private FilmService filmService;

    @Autowired
    private AppProperties appProperties;

    private CharacterImportService characterImportService;

    @BeforeEach
    void setUp() {
        characterImportService = new CharacterImportServiceImpl(characterConverter,filmService, restTemplate, appProperties);
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
        characterImportService.populateCharacters();

        //then
        verify(characterConverter, times(1)).toCharacter(any(), any());
    }

    @Test
    void givenUnknownUrl_whenPopulateCharacters_thenThrowException() {

        //given
        //when
        Mockito.when(restTemplate.getForObject(anyString(), eq(CharacterGet.class))).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        //then
        Exception e = assertThrows(HttpClientErrorException.class, () -> {
            characterImportService.populateCharacters();
        });
    }
}
