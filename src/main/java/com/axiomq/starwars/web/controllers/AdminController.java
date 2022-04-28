package com.axiomq.starwars.web.controllers;

import com.axiomq.starwars.services.CharacterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "Admin Controller", description = "Set of endpoints accessible only by ADMIN.")
public class AdminController {

    private final CharacterService characterService;

    @Operation(summary = "Gets data from SWAPI and populates Character table.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Characters added"),
            @ApiResponse(responseCode = "400", description = "Validation error : invalid argument"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("/script")
    public void populateCharacters() {
        characterService.populateCharacters();
    }
}
