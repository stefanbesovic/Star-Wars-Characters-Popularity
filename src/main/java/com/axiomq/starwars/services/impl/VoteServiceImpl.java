package com.axiomq.starwars.services.impl;

import com.axiomq.starwars.entities.Character;
import com.axiomq.starwars.entities.Vote;
import com.axiomq.starwars.enums.Role;
import com.axiomq.starwars.repositories.VoteRepository;
import com.axiomq.starwars.services.CharacterService;
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
import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final CharacterService characterService;
    private static final String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/icons";

    @Override
    public Vote saveVote(Vote vote, MultipartFile file, Long characterId, Principal principal) throws IOException {

        Character character = characterService.getCharacterById(characterId);
        vote.setCharacter(character);
        characterService.updateCharacterVotersCount(characterId, principal);

        Path path = saveFile(vote, file);
        vote.setIcon(path.getFileName().toString());
        vote.setUrl(path.toString());

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
    public Vote updateVote(Vote vote, MultipartFile icon, Long id) throws IOException{
        Vote existing = getVoteById(id);
        existing.setComment(vote.getComment());
        existing.setValue(vote.getValue());

        Path path = saveFile(vote, icon);

        existing.setIcon(path.getFileName().toString());
        existing.setUrl(path.toString());

        return voteRepository.save(existing);
    }

    @Override
    public void deleteVote(Long id) {
        Vote vote = getVoteById(id);
        voteRepository.deleteById(vote.getId());
    }

    private Path saveFile(Vote vote, MultipartFile file) throws IOException {

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path fullPath = Paths.get(uploadDirectory, fileName);

        try(InputStream inputStream = file.getInputStream()) {

            Files.copy(inputStream, fullPath, StandardCopyOption.REPLACE_EXISTING);
            return fullPath;

        } catch (IOException e) {
            throw new IOException(String.format("Can't upload file with name %s.", file.getOriginalFilename()));
        }
    }
}