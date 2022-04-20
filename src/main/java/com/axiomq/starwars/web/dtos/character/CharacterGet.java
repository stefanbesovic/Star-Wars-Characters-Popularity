package com.axiomq.starwars.web.dtos.character;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Character request")
public class CharacterGet {

    @Schema(description = "Next page of Characters")
    private String next;

    @Schema(description = "List of Characters")
    private List<CharacterResponse> results;
}
