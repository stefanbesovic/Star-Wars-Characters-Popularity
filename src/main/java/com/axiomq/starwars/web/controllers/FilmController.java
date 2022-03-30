package com.axiomq.starwars.web.controllers;

import com.axiomq.starwars.entities.Film;
import com.axiomq.starwars.services.FilmService;
import com.axiomq.starwars.web.dtos.film.FilmDto;
import com.axiomq.starwars.web.dtos.film.FilmMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/film")
public class FilmController {

    private final FilmService filmService;

    @PostMapping
    public FilmDto saveFilm(@RequestBody FilmDto filmDto) {
        return FilmMapper.INSTANCE.toDto(filmService.saveFilm(FilmMapper.INSTANCE.fromDto(filmDto)));
    }

    @GetMapping("/all")
    public List<FilmDto> getAllFilms() {
        return filmService.getAllFilms().stream()
                .map(FilmMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public FilmDto getFilmById(@PathVariable Long id) {
        return FilmMapper.INSTANCE.toDto(filmService.getFilmById(id));
    }

    @PutMapping("/{id}")
    public FilmDto updateFilm(@RequestBody FilmDto filmDto,
                           @PathVariable Long id) {
        return FilmMapper.INSTANCE.toDto(filmService.updateFilm(FilmMapper.INSTANCE.fromDto(filmDto), id));
    }

    @DeleteMapping("/{id}")
    public void deleteFilm(@PathVariable Long id) {
        filmService.deleteFilm(id);
    }
}
