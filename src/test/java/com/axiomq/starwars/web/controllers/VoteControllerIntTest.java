package com.axiomq.starwars.web.controllers;

import com.axiomq.starwars.web.dtos.vote.VoteRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = "/sql/voteIntTest.sql")
@ActiveProfiles("test")
@Transactional
class VoteControllerIntTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static String TOKEN;

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
    void givenVote_whenSaveVote_thenReturnOk() throws Exception {
        //given
        VoteRequest v = VoteRequest.builder()
                .value(1)
                .comment("Najs")
                .characterId(1L)
                .build();

        String voteJson = objectMapper.writeValueAsString(v);

        InputStream inputstream = getClass().getClassLoader().getResourceAsStream("icons/angry.png");
        MockMultipartFile icon = new MockMultipartFile("icon", "angry.png", MediaType.MULTIPART_FORM_DATA_VALUE, inputstream);
        MockMultipartFile json = new MockMultipartFile("request", "", MediaType.APPLICATION_JSON_VALUE, voteJson.getBytes());

        //when
        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/api/vote")
                .file(icon).file(json)
                        .header("Authorization", "Bearer " + TOKEN))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        //then
        mockMvc.perform(get("/api/vote/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + TOKEN))
                .andDo(print())
                .andExpect(jsonPath("$.value").value(1))
                .andExpect(jsonPath("$.comment").value("Najs"))
                .andExpect(jsonPath("$.icon").exists())
                .andExpect(jsonPath("$.url").exists())
                .andExpect(status().isOk())
                .andReturn();
    }
}