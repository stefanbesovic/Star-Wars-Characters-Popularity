package com.axiomq.starwars.web.controllers;

import com.axiomq.starwars.entities.Film;
import com.axiomq.starwars.repositories.FilmRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = "/sql/film-init.sql")
@ActiveProfiles("test")
@Transactional
class FilmControllerIntTest {

    private static String TOKEN;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FilmRepository filmRepository;

    @BeforeEach
    void setUp() throws Exception {
        MvcResult result = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"stefanbesovic@gmail.com\",\"password\":\"bass\"}"))
                .andDo(print())
                .andReturn();
        String content = result.getResponse().getHeader("Authorization");
        TOKEN = content.substring("Bearer ".length(), content.length());
    }

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
                        .content(json)
                        .header("Authorization", "Bearer " + TOKEN))
                .andDo(print())
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
                        .content(json)
                        .header("Authorization", "Bearer " + TOKEN))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}