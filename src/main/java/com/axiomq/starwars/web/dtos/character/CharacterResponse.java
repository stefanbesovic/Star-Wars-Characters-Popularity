package com.axiomq.starwars.web.dtos.character;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CharacterResponse {

    private String name;
    private String gender;
    private String homeworld;
    private Set<String> films;
}
