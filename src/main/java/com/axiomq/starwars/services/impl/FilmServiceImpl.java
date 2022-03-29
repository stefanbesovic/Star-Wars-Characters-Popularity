package com.axiomq.starwars.services.impl;

import com.axiomq.starwars.entities.Film;
import com.axiomq.starwars.repositories.FilmRepository;
import com.axiomq.starwars.services.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;

    @Override
    public Film saveFilm(Film film) {
        return filmRepository.save(film);
    }

    @Override
    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    @Override
    public Film getFilmById(Long id) {
        return filmRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("Film not found: %d", id)));
    }

    @Override
    public Film updateFilm(Film film, Long id) {
        Film existing = getFilmById(id);
        existing.setName(film.getName());
        return filmRepository.save(existing);
    }

    @Override
    public void deleteFilm(Long id) {
        Film film = getFilmById(id);
        filmRepository.deleteById(film.getId());
    }
}
