package com.axiomq.starwars.services;

import com.axiomq.starwars.entities.Vote;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface VoteService {
    Vote saveVote(Vote vote, MultipartFile file, Long characterId, Principal principal) throws IOException;
    List<Vote> getAllVotes();
    Vote getVoteById(Long id);
    Vote updateVote(Vote vote, MultipartFile file, Long id, Principal principal) throws IOException;
    void deleteVote(Long id, Principal principal);
    Integer getDistinctUsers(Long characterId);
}