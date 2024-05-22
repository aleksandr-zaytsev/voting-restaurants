package ru.azaytsev.votingrestaurants.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.azaytsev.votingrestaurants.common.error.DataConflictException;
import ru.azaytsev.votingrestaurants.model.Vote;
import ru.azaytsev.votingrestaurants.repository.VoteRepository;
import ru.azaytsev.votingrestaurants.service.VoteService;
import ru.azaytsev.votingrestaurants.user.AuthUser;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(value = UserVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class UserVoteController {

    static final String REST_URL = "/api/user/votes";

    private final VoteService voteService;
    private final VoteRepository voteRepository;

    @GetMapping("/actual")
    public Vote getOnToday(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get vote on today");
        return voteRepository.findByUserAndByDate(authUser.id(), LocalDate.now());
    }

    @GetMapping("/{voteDate}")
    public Vote getByDate(@AuthenticationPrincipal AuthUser authUser, @PathVariable LocalDate voteDate) {
        log.info("get vote on date {}", voteDate);
        Vote voteExisted = voteRepository.findByUserAndByDate(authUser.id(), voteDate);
        if (voteExisted == null) {
            throw new DataConflictException("Vote does not exist");
        }
        return voteExisted;
    }

    @GetMapping
    public List<Vote> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get all votes for auth user with id {}", authUser.id());
        return voteRepository.findAllByUser(authUser.id());
    }

    @PostMapping
    public ResponseEntity<Vote> createWithLocation(@AuthenticationPrincipal AuthUser authUser, @RequestParam Integer restaurantId) {
        log.info("create vote by restaurant id {} for user id {}", restaurantId, authUser.id());
        LocalDate voteDate = LocalDate.now();
        Vote created = voteService.create(authUser.id(), restaurantId, voteDate);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{voteDate}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping("/{voteDate}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser, @RequestParam Integer restaurantId) {
        voteService.update(authUser.id(), restaurantId, LocalDate.now(), LocalTime.now());
    }
}