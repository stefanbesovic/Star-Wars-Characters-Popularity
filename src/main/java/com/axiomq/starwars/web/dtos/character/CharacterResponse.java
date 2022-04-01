package com.axiomq.starwars.web.dtos.character;

import lombok.Data;

import java.util.Set;

@Data
public class CharacterResponse {

    private String name;
    private String gender;
    private String homeworld;
    private Set<String> films;
}
