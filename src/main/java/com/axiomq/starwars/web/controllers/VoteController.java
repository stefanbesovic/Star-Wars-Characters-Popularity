package com.axiomq.starwars.web.controllers;

import com.axiomq.starwars.entities.Vote;
import com.axiomq.starwars.services.VoteService;
import com.axiomq.starwars.web.dtos.vote.VoteMapper;
import com.axiomq.starwars.web.dtos.vote.VoteRequest;
import com.axiomq.starwars.web.dtos.vote.VoteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/vote")
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public VoteResponse saveVote(@RequestPart("icon") MultipartFile icon,
                                 @Valid @RequestPart("request") VoteRequest voteRequest,
                                 Principal principal) {
        Vote vote = voteService.saveVote(VoteMapper.INSTANCE.fromReqDto(voteRequest), icon, voteRequest.getCharacterId(), principal);
        return VoteMapper.INSTANCE.toResDto(vote);
    }

    @GetMapping
    public List<VoteResponse> getAllVotes() {
        return voteService.getAllVotes().stream()
                .map(VoteMapper.INSTANCE::toResDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public VoteResponse getVoteById(@PathVariable("id") Long id) {
        return VoteMapper.INSTANCE.toResDto(voteService.getVoteById(id))    ;
    }

    @PutMapping("/{id}")
    public VoteResponse updateVote(@PathVariable("id") Long id,
                                   @RequestPart("icon") MultipartFile icon,
                                   @Valid @RequestPart("request") VoteRequest voteRequest,
                                   Principal principal) {
        Vote vote = voteService.updateVote(VoteMapper.INSTANCE.fromReqDto(voteRequest), icon, id, principal);
        return VoteMapper.INSTANCE.toResDto(vote);
    }

    @DeleteMapping("/{id}")
    public void deleteVote(@PathVariable("id") Long id,
                           Principal principal) {
        voteService.deleteVote(id, principal);
    }
}