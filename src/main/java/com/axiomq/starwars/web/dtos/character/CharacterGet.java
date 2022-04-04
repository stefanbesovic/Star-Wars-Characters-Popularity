package com.axiomq.starwars.web.dtos.character;

import com.axiomq.starwars.entities.Character;
import lombok.Data;

import java.util.List;

@Data
public class CharacterGet {
    private String next;
    private List<CharacterResponse> results;
}
