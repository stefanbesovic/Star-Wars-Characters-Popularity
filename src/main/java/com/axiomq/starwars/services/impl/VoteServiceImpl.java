package com.axiomq.starwars.services.impl;

import com.axiomq.starwars.entities.Vote;
import com.axiomq.starwars.enums.Role;
import com.axiomq.starwars.repositories.VoteRepository;
import com.axiomq.starwars.services.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class VoteServiceImpl implements VoteService {
    private final VoteRepository voteRepository;
    private static final String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/icons";

    @Override
    public Vote saveVote(Vote vote, MultipartFile file) throws IOException {

        Path fullPath = Paths.get(uploadDirectory, "id_" + vote.getId() + "_" + file.getOriginalFilename());
        try(InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, fullPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException(String.format("Can't upload file with name %s.", file.getOriginalFilename()));
        }

        vote.setIcon(file.getOriginalFilename());
        vote.setUrl(fullPath.toString());

        return voteRepository.save(vote);
    }

    @Override
    public List<Vote> getAllVotes() {
        return voteRepository.findAll();
    }

    @Override
    public Vote getVoteById(Long id) {
        return voteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("Vote not found: %d", id)));
    }

    @Override
    public Vote updateVote(Vote vote, Long id) {
        Vote existing = getVoteById(id);
        existing.setComment(vote.getComment());
        existing.setValue(vote.getValue());
        existing.setIcon(vote.getIcon()); //change this

        return voteRepository.save(existing);
    }

    @Override
    public void deleteVote(Long id) {
        Vote vote = getVoteById(id);
        voteRepository.deleteById(vote.getId());
    }
}