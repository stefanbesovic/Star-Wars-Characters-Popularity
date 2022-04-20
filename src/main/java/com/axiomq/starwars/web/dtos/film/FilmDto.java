package com.axiomq.starwars.web.dtos.film;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Film request")
public class FilmDto {

    @Schema(description = "Film's name")
    @NotEmpty
    @Size(min = 3, max = 40, message = "Film name should be between 3 and 40 characters")
    private String name;
}
