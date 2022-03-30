package com.axiomq.starwars.web.dtos.film;

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
public class FilmDto {

    @NotEmpty
    @Size(min = 3, max = 40, message = "Film name should be between 3 and 40 characters")
    private String name;
}
