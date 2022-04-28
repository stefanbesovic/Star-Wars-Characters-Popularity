package com.axiomq.starwars.web.controllers;

import com.axiomq.starwars.services.FilmService;
import com.axiomq.starwars.web.dtos.film.FilmDto;
import com.axiomq.starwars.web.dtos.film.FilmMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/film")
@Tag(name = "Film Controller", description = "Set of endpoints for Creating, Retrieving, Updating and Deleting of Film.")
public class FilmController {

    private final FilmService filmService;

    @Operation(summary = "Creates new Film")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Film"),
            @ApiResponse(responseCode = "400", description = "Validation error : invalid argument"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping
    public FilmDto saveFilm(@RequestBody FilmDto filmDto) {
        return FilmMapper.INSTANCE.toDto(filmService.saveFilm(FilmMapper.INSTANCE.fromDto(filmDto)));
    }

    @Operation(summary = "Retrieves list of all Films")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of Films"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping
    public List<FilmDto> getAllFilms() {
        return filmService.getAllFilms().stream()
                .map(FilmMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Retrieves details about Film")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Film"),
            @ApiResponse(responseCode = "400", description = "Validation error : invalid argument"),
            @ApiResponse(responseCode = "404", description = "Film not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{id}")
    public FilmDto getFilmById(@PathVariable Long id) {
        return FilmMapper.INSTANCE.toDto(filmService.getFilmById(id));
    }

    @Operation(summary = "Updates existing Film")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Film"),
            @ApiResponse(responseCode = "400", description = "Validation error : invalid argument"),
            @ApiResponse(responseCode = "404", description = "Film not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("/{id}")
    public FilmDto updateFilm(@RequestBody FilmDto filmDto,
                           @PathVariable Long id) {
        return FilmMapper.INSTANCE.toDto(filmService.updateFilm(FilmMapper.INSTANCE.fromDto(filmDto), id));
    }

    @Operation(summary = "Deletes Film")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Film deleted"),
            @ApiResponse(responseCode = "404", description = "Film not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/{id}")
    public void deleteFilm(@PathVariable Long id) {
        filmService.deleteFilm(id);
    }
}
