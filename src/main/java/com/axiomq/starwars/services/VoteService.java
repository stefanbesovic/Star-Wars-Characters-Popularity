package com.axiomq.starwars.services;

import com.axiomq.starwars.entities.Vote;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

public interface VoteService {
    Vote saveVote(Vote vote, MultipartFile file, Long characterId, Principal principal);
    List<Vote> getAllVotes();
    Vote getVoteById(Long id);
    Vote updateVote(Vote vote, MultipartFile file, Long id, Principal principal);
    void deleteVote(Long id, Principal principal);
    Integer getDistinctUsers(Long characterId);
}
