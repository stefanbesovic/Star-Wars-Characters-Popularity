package com.axiomq.starwars.services;

import com.axiomq.starwars.entities.Film;
import com.axiomq.starwars.entities.User;

import java.util.List;

public interface FilmService {
    Film saveFilm(Film film);
    List<Film> getAllFilms();
    Film getFilmById(Long id);
    Film updateFilm(Film film, Long id);
    void deleteFilm(Long id);
}
