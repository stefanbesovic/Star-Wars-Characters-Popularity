package com.axiomq.starwars.web.controllers;

import com.axiomq.starwars.entities.Film;
import com.axiomq.starwars.repositories.FilmRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class FilmControllerIntTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FilmRepository filmRepository;

    @Test
    void givenFilm_whenSaveFilm_thenOk() throws Exception {
        //given
        Film film = Film.builder()
                .name("movie")
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(film);

        //when
        mockMvc.perform(post("/api/film")
                        .contentType("application/json")
                        .content(json))
                .andDo(print())
                .andExpect(jsonPath("$.name").value("movie"))
                .andExpect(status().isOk());

        //then
        List<Film> films = filmRepository.findAll();
        Film ff = films.get(films.size() - 1);

        assertNotNull(ff);
        assertEquals("movie",  ff.getName());
    }

    @Test
    void givenNullFilm_whenSaveFilm_thenBadRequest() throws Exception {
        //given
        Film film = null;

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(film);

        //when //then
        mockMvc.perform(post("/api/film")
                        .contentType("application/json")
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

}