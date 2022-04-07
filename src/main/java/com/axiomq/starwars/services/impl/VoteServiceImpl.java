package com.axiomq.starwars.services.impl;

import com.axiomq.starwars.entities.Character;
import com.axiomq.starwars.entities.User;
import com.axiomq.starwars.entities.Vote;
import com.axiomq.starwars.exceptions.ObjectNotFoundException;
import com.axiomq.starwars.repositories.VoteRepository;
import com.axiomq.starwars.services.CharacterService;
import com.axiomq.starwars.services.UserService;
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
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private static final String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/icons";
    private final CharacterService characterService;
    private final UserService userService;

    @Override
    public Vote saveVote(Vote vote, MultipartFile file, Long characterId, Principal principal) throws IOException {

        User byEmail = userService.findByEmail(principal.getName());
        vote.setUser(byEmail);

        Character character = characterService.getCharacterById(characterId);
        vote.setCharacter(character);

        Path path = saveFile(vote, file);
        vote.setIcon(path.getFileName().toString());
        vote.setUrl(path.toString());

        Vote saved = voteRepository.save(vote);

        character.setVotersCount(getDistinctUsers(characterId));
        characterService.updateCharacter(character, characterId);

        return saved;
    }

    @Override
    public List<Vote> getAllVotes() {
        return voteRepository.findAll();
    }

    @Override
    public Vote getVoteById(Long id) {
        return voteRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Vote not found: %d", id)));
    }

    @Override
    public Vote updateVote(Vote vote, MultipartFile icon, Long id, Principal principal) throws IOException{
        Vote existing = getVoteById(id);

        if(!existing.getUser().getEmail().equals(principal.getName()))
            throw new ObjectNotFoundException("You are not authorized for this action.");

        existing.setComment(vote.getComment());
        existing.setValue(vote.getValue());

        Path path = saveFile(vote, icon);
        existing.setIcon(path.getFileName().toString());
        existing.setUrl(path.toString());

        return voteRepository.save(existing);
    }

    @Override
    public void deleteVote(Long id, Principal principal) {
        Vote vote = getVoteById(id);
        if(!vote.getUser().getEmail().equals(principal.getName()))
            throw new ObjectNotFoundException("You are not authorized for this action.");

        Character character = vote.getCharacter();
        voteRepository.deleteById(vote.getId());

        character.setVotersCount(getDistinctUsers(character.getId()));
        characterService.updateCharacter(character, character.getId());
    }

    @Override
    public Integer getDistinctUsers(Long characterId) {
        return voteRepository.countDistinctUsers(characterId);
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