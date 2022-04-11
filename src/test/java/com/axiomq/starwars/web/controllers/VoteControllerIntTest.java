package com.axiomq.starwars.web.controllers;

import com.axiomq.starwars.web.dtos.vote.VoteRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.InputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    private final static String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdGVmYW5iZXNvdmljQGdtYWlsLmNvbSIsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJBRE1JTiJ9XSwiaWF0IjoxNjQ5MTQ3NTQ1LCJleHAiOjE2NDk5NzM2MDB9.jv2nvSsPe6r_SuinLuXkarpq-o38ihtyRXGKEDCyHbs";

    @Test
    void givenVote_whenSaveVote_thenReturnOk() throws Exception {
        //given
        VoteRequest v = VoteRequest.builder()
                .value(1)
                .comment("Najs")
                .characterId(1L)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String voteJson = objectMapper.writeValueAsString(v);

        InputStream inputstream = new FileInputStream("/home/stefan/Downloads/angry.png");
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