package com.axiomq.starwars.web.controllers;

import com.axiomq.starwars.entities.Film;
import com.axiomq.starwars.repositories.FilmRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Transactional
class CharacterControllerIntTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private FilmRepository  filmRepository;

    @BeforeEach
    void setUp() {
        filmRepository.save(Film.builder().name("a").build());
        filmRepository.save(Film.builder().name("ab").build());
        filmRepository.save(Film.builder().name("ac").build());
        filmRepository.save(Film.builder().name("ad").build());
        filmRepository.save(Film.builder().name("ae").build());
        filmRepository.save(Film.builder().name("az").build());
    }

    @Test
    void givenCorrectURL_whenPopulateCharacters_ReturnOk() throws Exception {
        //given
        //when
        mockMvc.perform(post("/api/character/script")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        //then
        mockMvc.perform(get("/api/character")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(10)))
                .andReturn();
    }
}