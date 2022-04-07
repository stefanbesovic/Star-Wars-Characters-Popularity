package com.axiomq.starwars.web.dtos.character;

import com.axiomq.starwars.entities.Film;
import com.axiomq.starwars.enums.Gender;
import com.axiomq.starwars.enums.Planet;
import com.axiomq.starwars.services.FilmService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static com.axiomq.starwars.enums.Planet.SOME_OTHER_PLANET;
import static com.axiomq.starwars.enums.Planet.TATOOINE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ConvertCharacterTest {

    @Mock
    private FilmService filmService;

    private ConvertCharacter convertCharacter;

    @BeforeEach
    void setUp() {
        convertCharacter = new ConvertCharacter(filmService);
    }

    @Test
    void givenHomeworld_whenConvertGender_returnPlanet() {
        //given
        String homewolrd = "https://swapi.dev/api/planets/1/";

        //when
        Planet planet = convertCharacter.convertHomeworld(homewolrd);

        //then
        assertNotNull(planet);
        assertEquals(TATOOINE, planet);
    }

    @Test
    void givenHomeworldNull_whenConvertGender_returnThrowException() {
        //given
        String homewolrd = null;

        //when
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> convertCharacter.convertHomeworld(homewolrd)
        );

        //then
        assertTrue(e.getMessage().contains("Homeworld is null"));
    }

    @Test
    void givenHomeworldGreaterThanSix_whenConvertGender_returnSomeOtherPlanet() {
        //given
        String homewolrd = "https://swapi.dev/api/planets/7/";

        //when
        Planet planet = convertCharacter.convertHomeworld(homewolrd);

        //then
        assertNotNull(planet);
        assertEquals(SOME_OTHER_PLANET, planet);
    }

    @Test
    void givenStringSet_whenConvertFilm_returnOk() {
        //given
        String film1 = "https://swapi.dev/api/films/1/";
        String film2 = "https://swapi.dev/api/films/2/";

        Set<String> filmStrings = Set.of(film1, film2);

        //when
        convertCharacter.convertFilm(filmStrings);

        //then
        Mockito.verify(filmService, Mockito.times(filmStrings.size())).getFilmById(any());
    }

    @Test
    void givenNullStringSet_whenConvertFilm_return() {
        //given
        Set<String> filmStrings = null;

        //when
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> convertCharacter.convertFilm(filmStrings));

        //then
        assertTrue(e.getMessage().contains("Films are null."));
    }

    @Test
    void givenGoodFormat_whenGetPlainNumber_thenReturnInteger() {
        //given
        String prefix = "https://swapi.dev/api/planets/";
        String name = "https://swapi.dev/api/planets/1/";

        //when
        Integer number = convertCharacter.getPlainNumber(prefix, name);

        //then
        assertNotNull(number);
        assertEquals(1, number);
    }

    @Test
    void givenBadStringFormat_whenGetPlainNumber_thenThrowNumberFormatException() {
        //given
        String prefix = "https://swapi.dev/api/planets/";
        String name = "https://swapi.dev/api/planets/s/";

        //when
        //then
        Exception e = assertThrows(NumberFormatException.class,
                () -> convertCharacter.getPlainNumber(prefix, name));

    }

    @Test
    void givenBadNegativeNumberFormat_whenGetPlainNumber_thenThrowNumberFormatException() {
        //given
        String prefix = "https://swapi.dev/api/planets/";
        String name = "https://swapi.dev/api/planets/-1/";

        //when
        //then
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> convertCharacter.getPlainNumber(prefix, name));

        assertTrue(e.getMessage().contains("Number has negative value!"));
    }

    @Test
    void givenGenderAsString_whenConvertGender_thenReturnGender() {
        //given
        String genderString = "male";

        //when
        Gender gender = convertCharacter.convertGender(genderString);

        //then
        assertNotNull(gender);
        assertEquals(Gender.MALE, gender);
    }

    @Test
    void givenNAGenderAsString_whenConvertGender_thenReturnGender() {
        //given
        String genderString = "n/a";

        //when
        Gender gender = convertCharacter.convertGender(genderString);

        //then
        assertNotNull(gender);
        assertEquals(Gender.N_A, gender);
    }

    @Test
    void givenNullGenderAsString_whenConvertGender_thenThrowException() {
        //given
        String genderString = null;

        //when
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> convertCharacter.convertGender(genderString));

        //then
        assertTrue(e.getMessage().contains("Gender is null."));
    }

    @Test
    void givenBadFormatGenderAsString_whenConvertGender_thenThrowException() {
        //given
        String genderString = "badFormat";

        //when
        //then
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> convertCharacter.convertGender(genderString));

    }
}