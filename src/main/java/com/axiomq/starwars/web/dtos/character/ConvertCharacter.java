package com.axiomq.starwars.web.dtos.character;

import com.axiomq.starwars.entities.Character;
import com.axiomq.starwars.entities.Film;
import com.axiomq.starwars.enums.Gender;
import com.axiomq.starwars.enums.Planet;
import com.axiomq.starwars.services.FilmService;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ConvertCharacter {

    private final FilmService filmService;

    public Character toCharacter(CharacterResponse characterResponse) {
        return Character.builder()
                .name(characterResponse.getName())
                .planet(convertHomeworld(characterResponse.getHomeworld()))
                .gender(convertGender(characterResponse.getGender()))
                .films(convertFilm(characterResponse.getFilms()))
                .votersCount(0)
                .build();
    }

    public Gender convertGender(String gender) {
        if(gender.equals("n/a"))
            return Gender.UNKNOWN;
        return Gender.valueOf(gender.toUpperCase());
    }

    public Planet convertHomeworld(String homeworld) {
        String prefix = "https://swapi.dev/api/planets/";

        int planet = getPlainNumber(prefix, homeworld) - 1;

        if(planet > 6)
            return Planet.SOME_OTHER_PLANET;

        return Planet.values()[planet];
    }

    public Set<Film> convertFilm(Set<String> films) {
        String prefix = "https://swapi.dev/api/films/";
        Set<Film> filmSet = new HashSet<>();
        Set<Long> filmIds = films.stream()
                .map(film -> Long.valueOf(getPlainNumber(prefix, film)))
                .collect(Collectors.toSet());

        filmSet = filmIds.stream()
                .map(filmService::getFilmById)
                .collect(Collectors.toSet());

        return filmSet;
    }

    public int getPlainNumber(String prefix, String name) {
        String numberAsString = name.substring(0, name.length() - 1);
        numberAsString = numberAsString.substring(prefix.length());

        return Integer.parseInt(numberAsString);
    }
}
