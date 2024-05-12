package ru.azaytsev.votingrestaurants.user.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.azaytsev.votingrestaurants.user.model.User;
import ru.azaytsev.votingrestaurants.user.model.Vote;
import ru.azaytsev.votingrestaurants.user.repository.VoteRepository;
import ru.azaytsev.votingrestaurants.user.service.VoteService;

import java.net.URI;
import java.time.LocalDate;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class VoteController {

    static final String REST_URL = "/api/user/votes";

    protected VoteService voteService;

    @PostMapping
    public ResponseEntity<Vote> create(@RequestParam Integer restaurantId, @AuthenticationPrincipal User authUser) {
        Vote newVote = voteService.addVote(authUser.getId(), restaurantId, LocalDate.now());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(newVote.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(newVote);
    }
}
