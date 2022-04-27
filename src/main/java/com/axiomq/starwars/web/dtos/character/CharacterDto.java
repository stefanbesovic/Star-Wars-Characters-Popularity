package com.axiomq.starwars.web.dtos.character;

import com.axiomq.starwars.enums.Gender;
import com.axiomq.starwars.enums.Planet;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Character response")
public class CharacterDto {

    @Schema(description = "Character's id")
    private Long id;

    @Schema(description = "Character's name")
    private String name;

    @Schema(description = "Character's gender")
    private Gender gender;

    @Schema(description = "Character's planet")
    private Planet planet;

    @Schema(description = "Character's voters")
    private Integer votersCount;
}
