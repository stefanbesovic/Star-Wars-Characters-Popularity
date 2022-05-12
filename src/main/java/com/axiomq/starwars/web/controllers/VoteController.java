package com.axiomq.starwars.web.controllers;

import com.axiomq.starwars.entities.Vote;
import com.axiomq.starwars.services.VoteService;
import com.axiomq.starwars.web.dtos.vote.VoteMapper;
import com.axiomq.starwars.web.dtos.vote.VoteRequest;
import com.axiomq.starwars.web.dtos.vote.VoteResponse;
import com.axiomq.starwars.web.dtos.vote.VoteUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/vote")
@Tag(name = "Vote Controller", description = "Set of endpoints for Creating, Retrieving, Updating and Deleting of Vote.")
public class VoteController {

    private final VoteService voteService;

    @Operation(summary = "Creates new Vote")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vote"),
            @ApiResponse(responseCode = "400", description = "Validation error : invalid argument"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping
    public VoteResponse saveVote(@RequestPart(value = "icon") MultipartFile icon,
                                 @Valid @RequestPart("request") VoteRequest voteRequest,
                                 Principal principal) {
        Vote vote = voteService.saveVote(VoteMapper.INSTANCE.fromReqDto(voteRequest), icon, voteRequest.getCharacterId(), principal);
        return VoteMapper.INSTANCE.toResDto(vote);
    }

    @Operation(summary = "Retrieves list of all Votes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of Votes"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping
    public List<VoteResponse> getAllVotes() {
        return voteService.getAllVotes().stream()
                .map(VoteMapper.INSTANCE::toResDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Retrieves details about Vote")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vote"),
            @ApiResponse(responseCode = "400", description = "Validation error : invalid argument"),
            @ApiResponse(responseCode = "404", description = "Vote not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{id}")
    public VoteResponse getVoteById(@PathVariable("id") Long id) {
        return VoteMapper.INSTANCE.toResDto(voteService.getVoteById(id))    ;
    }

    @Operation(summary = "Updates existing Vote")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vote"),
            @ApiResponse(responseCode = "400", description = "Validation error : invalid argument"),
            @ApiResponse(responseCode = "404", description = "Vote not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("/{id}")
    public VoteResponse updateVote(@PathVariable("id") Long id,
                                   @RequestPart(value = "icon", required = false) MultipartFile icon,
                                   @Valid @RequestPart("request") VoteUpdateDto voteUpdateDto,
                                   Principal principal) {
        Vote vote = voteService.updateVote(VoteMapper.INSTANCE.fromUpdDto(voteUpdateDto), icon, id, principal);
        return VoteMapper.INSTANCE.toResDto(vote);
    }

    @Operation(summary = "Deletes Vote")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vote deleted"),
            @ApiResponse(responseCode = "404", description = "Vote not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/{id}")
    public void deleteVote(@PathVariable("id") Long id,
                           Principal principal) {
        voteService.deleteVote(id, principal);
    }
}
