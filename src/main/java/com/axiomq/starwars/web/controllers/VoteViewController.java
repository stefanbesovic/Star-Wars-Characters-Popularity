package com.axiomq.starwars.web.controllers;

import com.axiomq.starwars.entities.Character;
import com.axiomq.starwars.entities.Vote;
import com.axiomq.starwars.services.CharacterService;
import com.axiomq.starwars.services.VoteService;
import com.axiomq.starwars.web.dtos.vote.VoteMapper;
import com.axiomq.starwars.web.dtos.vote.VoteRequest;
import com.axiomq.starwars.web.dtos.vote.VoteResponse;
import com.axiomq.starwars.web.dtos.vote.VoteUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/vote")
public class VoteViewController {

    private final CharacterService characterService;
    private final VoteService voteService;

    @GetMapping("/all")
    public ModelAndView voteView(Model model) {
        VoteRequest v = new VoteRequest();
        List<Character> characters = characterService.getAllCharacters();
        List<VoteResponse> votes = voteService.getAllVotes()
                .stream()
                .map(VoteMapper.INSTANCE::toResDto)
                .collect(Collectors.toList());

        model.addAttribute("votes", votes);
        model.addAttribute("vote", v);
        model.addAttribute("characters", characters);

        return new ModelAndView("vote");
    }

    @PostMapping("/save")
    public ModelAndView saveVote(@RequestPart("icon") MultipartFile icon,
                                 @Valid @ModelAttribute VoteRequest voteRequest,
                                 Principal principal) {
        Vote vote = voteService.saveVote(VoteMapper.INSTANCE.fromReqDto(voteRequest), icon, voteRequest.getCharacterId(), principal);
        return new ModelAndView("redirect:/api/vote/all");
    }

    @GetMapping("/edit")
    public ModelAndView editVoteView(@RequestParam("voteId") Long id, Model model) {
        Vote vote = voteService.getVoteById(id);
        model.addAttribute("vote", vote);
        model.addAttribute("num", vote.getValue());
        return new ModelAndView("vote_edit");
    }

    @PostMapping("/edit")
    public ModelAndView editVote(@RequestPart(value = "icon", required = false) MultipartFile icon,
                                 @Valid @ModelAttribute("vote") VoteUpdateDto voteUpdateDto,
                                 Principal principal) {

        Vote vote = voteService.updateVote(VoteMapper.INSTANCE.fromUpdDto(voteUpdateDto), icon, voteUpdateDto.getId(), principal);

        return new ModelAndView("redirect:/api/vote/all");
    }

    @GetMapping("/delete")
    public ModelAndView editVote(@RequestParam("voteId") Long id,
                                 Principal principal) {

        voteService.deleteVote(id, principal);

        return new ModelAndView("redirect:/api/vote/all");
    }
}
