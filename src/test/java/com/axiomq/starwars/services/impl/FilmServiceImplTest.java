package com.axiomq.starwars.services.impl;

import com.axiomq.starwars.entities.Film;
import com.axiomq.starwars.repositories.FilmRepository;
import com.axiomq.starwars.services.FilmService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilmServiceImplTest {
    
    @Mock
    private FilmRepository filmRepository;

    private FilmService filmService;

    @BeforeEach
    void setUp() {
        filmService = new FilmServiceImpl(filmRepository);
    }

    @Test
    void givenFilm_whenGetFilmById_thenOk() {
        //given
        Film film = Film.builder()
                .name("stefan")
                .build();

        //when
        when(filmRepository.findById(film.getId())).thenReturn(Optional.of(film));
        Film actual = filmService.getFilmById(film.getId());

        //then
        verify(filmRepository, times(1)).findById(film.getId());
        assertNotNull(actual);
        assertEquals(film.getName(), actual.getName());
    }

    @Test
    void givenNonExistingFilm_whenGetFilmById_thenThrowNoSuchElementException() {
        //given
        Film film = new Film();

        //when
        when(filmRepository.findById(film.getId())).thenReturn(Optional.empty());
        Exception e = assertThrows(NoSuchElementException.class, () -> {
            filmService.getFilmById(film.getId());
        });

        //then
        assertTrue(e.getMessage().contains(String.format("Film not found: %d", film.getId())));
    }

    @Test
    void givenUpdatedFilm_whenUpdateFilm_ThenOk() {
        //given
        Film film = Film.builder()
                .name("stefan")
                .build();

        //when
        when(filmRepository.findById(film.getId())).thenReturn(Optional.of(film));
        when(filmRepository.save(film)).thenReturn(Film.builder()
                .name("stefi")
                .build());

        Film actual = filmService.updateFilm(film, film.getId());

        //then
        assertNotNull(actual);
        assertEquals("stefi", actual.getName());
    }
}