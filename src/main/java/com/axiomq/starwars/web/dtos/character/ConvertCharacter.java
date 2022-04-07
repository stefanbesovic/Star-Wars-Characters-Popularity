package com.axiomq.starwars.web.dtos.character;

import com.axiomq.starwars.entities.Character;
import com.axiomq.starwars.entities.Film;
import com.axiomq.starwars.enums.Gender;
import com.axiomq.starwars.enums.Planet;
import com.axiomq.starwars.services.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
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
        if(gender == null)
            throw new IllegalArgumentException("Gender is null.");

        if(gender.equals("n/a"))
            return Gender.N_A;
        return Gender.valueOf(gender.toUpperCase());
    }

    public Planet convertHomeworld(String homeworld) {
        if(homeworld == null)
            throw new IllegalArgumentException("Homeworld is null.");

        String prefix = "https://swapi.dev/api/planets/";

        int planet = getPlainNumber(prefix, homeworld) - 1;

        if(planet > 6)
            return Planet.SOME_OTHER_PLANET;

        return Planet.values()[planet];
    }

    public Set<Film> convertFilm(Set<String> films) {
        if(films == null)
            throw new IllegalArgumentException("Films are null.");

        String prefix = "https://swapi.dev/api/films/";
        Set<Long> filmIds = films.stream()
                .map(film -> Long.valueOf(getPlainNumber(prefix, film)))
                .collect(Collectors.toSet());

        return filmIds.stream()
                .map(filmService::getFilmById)
                .collect(Collectors.toSet());
    }

    public int getPlainNumber(String prefix, String name) {
        String numberAsString = name.substring(0, name.length() - 1);
        numberAsString = numberAsString.substring(prefix.length());

        int number = Integer.parseInt(numberAsString);
        if(number <= 0)
            throw new IllegalArgumentException("Number has negative value!");

        return  number;
    }
}
