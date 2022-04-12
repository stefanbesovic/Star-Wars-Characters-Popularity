package com.axiomq.starwars.web.dtos.character;

import com.axiomq.starwars.config.AppProperties;
import com.axiomq.starwars.entities.Film;
import com.axiomq.starwars.enums.Gender;
import com.axiomq.starwars.enums.Planet;
import com.axiomq.starwars.services.FilmService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.axiomq.starwars.enums.Planet.SOME_OTHER_PLANET;
import static com.axiomq.starwars.enums.Planet.TATOOINE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@EnableConfigurationProperties(value = AppProperties.class)
@TestPropertySource("classpath:application.properties")
class ConvertCharacterTest {

    @Mock
    private FilmService filmService;

    @Autowired
    private AppProperties appProperties;

    private CharacterConverter characterConverter;

    private final List<Film> films = new ArrayList<>();

    @BeforeEach
    void setUp() {
        characterConverter = new CharacterConverter(filmService, appProperties);
        films.add(Film.builder().id(1L).name("aa").build());
        films.add(Film.builder().id(2L).name("bb").build());
        films.add(Film.builder().id(3L).name("cc").build());
        films.add(Film.builder().id(4L).name("dd").build());
        films.add(Film.builder().id(5L).name("ee").build());
        films.add(Film.builder().id(6L).name("gg").build());
    }

    @Test
    void givenHomeworld_whenConvertGender_returnPlanet() {
        //given
        String prefix = appProperties.getPlanets();
        String homewolrd = prefix + "1/";

        //when
        Planet planet = characterConverter.convertHomeworld(homewolrd);

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
                () -> characterConverter.convertHomeworld(homewolrd)
        );

        //then
        assertTrue(e.getMessage().contains("Homeworld is null"));
    }

    @Test
    void givenHomeworldGreaterThanSix_whenConvertGender_returnSomeOtherPlanet() {
        //given
        String prefix = appProperties.getPlanets();
        String homewolrd = prefix + "7/";

        //when
        Planet planet = characterConverter.convertHomeworld(homewolrd);

        //then
        assertNotNull(planet);
        assertEquals(SOME_OTHER_PLANET, planet);
    }

    @Test
    void givenStringSet_whenConvertFilm_returnOk() {
        //given
        String prefix = appProperties.getFilms();
        String film1 = prefix + "1/";
        String film2 = prefix + "2/";

        Set<String> filmStrings = Set.of(film1, film2);

        //when
        Set<Film> film = characterConverter.convertFilm(filmStrings, films);

        //then
        assertNotNull(film);
        assertFalse(film.isEmpty());
        assertEquals(2, film.size());
        assertEquals("aa", film.iterator().next().getName());
    }

    @Test
    void givenNullStringSet_whenConvertFilm_return() {
        //given
        Set<String> filmStrings = null;

        //when
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> characterConverter.convertFilm(filmStrings, films));

        //then
        assertTrue(e.getMessage().contains("Films are null."));
    }

    @Test
    void givenGoodFormat_whenGetPlainNumber_thenReturnInteger() {
        //given
        String prefix = appProperties.getPlanets();
        String name = prefix + "1/";

        //when
        Integer number = characterConverter.getPlainNumber(prefix, name);

        //then
        assertNotNull(number);
        assertEquals(1, number);
    }

    @Test
    void givenBadStringFormat_whenGetPlainNumber_thenThrowNumberFormatException() {
        //given
        String prefix = appProperties.getPlanets();
        String name = prefix + "s/";

        //when
        //then
        Exception e = assertThrows(NumberFormatException.class,
                () -> characterConverter.getPlainNumber(prefix, name));

    }

    @Test
    void givenGenderAsString_whenConvertGender_thenReturnGender() {
        //given
        String genderString = "male";

        //when
        Gender gender = characterConverter.convertGender(genderString);

        //then
        assertNotNull(gender);
        assertEquals(Gender.MALE, gender);
    }

    @Test
    void givenNAGenderAsString_whenConvertGender_thenReturnGender() {
        //given
        String genderString = "n/a";

        //when
        Gender gender = characterConverter.convertGender(genderString);

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
                () -> characterConverter.convertGender(genderString));

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
                () -> characterConverter.convertGender(genderString));

    }
}