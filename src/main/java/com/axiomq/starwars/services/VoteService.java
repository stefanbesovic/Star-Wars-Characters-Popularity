package com.axiomq.starwars.services;

import com.axiomq.starwars.entities.Vote;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VoteService {
    Vote saveVote(Vote vote, MultipartFile file) throws IOException;
    List<Vote> getAllVotes();
    Vote getVoteById(Long id);
    Vote updateVote(Vote vote, Long id);
    void deleteVote(Long id);
}