package com.axiomq.starwars.services.impl;

import com.axiomq.starwars.entities.Film;
import com.axiomq.starwars.exceptions.ObjectNotFoundException;
import com.axiomq.starwars.repositories.FilmRepository;
import com.axiomq.starwars.services.FilmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;

    @Override
    public Film saveFilm(Film film) {
        log.info("Saving film '{}' to database.", film.getName());
        return filmRepository.save(film);
    }

    @Override
    public List<Film> getAllFilms() {
        log.info("Getting list of all films.");
        return filmRepository.findAll();
    }

    @Override
    public Film getFilmById(Long id) {
        log.info("Getting film by id: {}", id);
        return filmRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Film not found: %d", id)));
    }

    @Override
    public Film updateFilm(Film film, Long id) {
        log.info("Updating film with id: {}.", id);
        Film existing = getFilmById(id);
        existing.setName(film.getName());
        return filmRepository.save(existing);
    }

    @Override
    public void deleteFilm(Long id) {
        log.info("Deleting film with id {}", id);
        Film film = getFilmById(id);
        filmRepository.deleteById(film.getId());
    }
}
