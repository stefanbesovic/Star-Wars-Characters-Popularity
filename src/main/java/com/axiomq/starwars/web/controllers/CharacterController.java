package com.axiomq.starwars.web.controllers;

import com.axiomq.starwars.entities.Character;
import com.axiomq.starwars.services.CharacterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/character")
@Tag(name = "Character Controller", description = "Set of endpoints for Retrieving and Deleting of Character.")
public class CharacterController {

    private final CharacterService characterService;

    @Operation(summary = "Retrieves list of all Characters")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of Characters"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping
    public List<Character> getAllCharacters() {
        return characterService.getAllCharacters();
    }

    @Operation(summary = "Deletes Character")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Character deleted"),
            @ApiResponse(responseCode = "404", description = "Character not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/{id}")
    public void deleteCharacter(@PathVariable("id") Long id) {
        characterService.deleteCharacter(id);
    }

}
