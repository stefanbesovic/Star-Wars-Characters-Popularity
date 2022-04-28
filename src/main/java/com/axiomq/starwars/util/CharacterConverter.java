package com.axiomq.starwars.util;

import com.axiomq.starwars.config.AppProperties;
import com.axiomq.starwars.entities.Character;
import com.axiomq.starwars.entities.Film;
import com.axiomq.starwars.enums.Gender;
import com.axiomq.starwars.enums.Planet;
import com.axiomq.starwars.web.dtos.character.CharacterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CharacterConverter {

    private final AppProperties appProperties;

    public Character toCharacter(CharacterResponse characterResponse, List<Film> films) {
        return Character.builder()
                .name(characterResponse.getName())
                .planet(convertHomeworld(characterResponse.getHomeworld()))
                .gender(convertGender(characterResponse.getGender()))
                .films(convertFilm(characterResponse.getFilms(), films))
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

        String prefix = appProperties.getPlanets();
        int planet = getPlainNumber(prefix, homeworld) - 1;

        if(planet > 6)
            return Planet.SOME_OTHER_PLANET;

        return Planet.values()[planet];
    }

    public Set<Film> convertFilm(Set<String> films, List<Film> existingFilms) {
        if(films == null)
            throw new IllegalArgumentException("Films are null.");

        String prefix = appProperties.getFilms();
        Set<Long> filmIds = films.stream()
                .map(film -> Long.valueOf(getPlainNumber(prefix, film)))
                .collect(Collectors.toSet());

        return filmIds.stream()
                .map(id -> existingFilms.get((int)(id - 1)))
                .collect(Collectors.toSet());
    }

    public int getPlainNumber(String prefix, String name) {
        String numberAsString = name.substring(0, name.length() - 1);
        numberAsString = numberAsString.substring(prefix.length());

        try {
            return Integer.parseInt(numberAsString);

        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid number format");
        }
    }
}
