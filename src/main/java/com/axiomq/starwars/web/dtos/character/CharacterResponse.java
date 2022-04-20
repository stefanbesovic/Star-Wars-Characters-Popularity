package com.axiomq.starwars.web.dtos.character;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Character response")
public class CharacterResponse {

    @Schema(description = "Character's name")
    private String name;

    @Schema(description = "Character's gender")
    private String gender;

    @Schema(description = "Character's planet/homeworld")
    private String homeworld;

    @Schema(description = "Character's films")
    private Set<String> films;
}
