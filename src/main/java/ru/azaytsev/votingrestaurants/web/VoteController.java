package ru.azaytsev.votingrestaurants.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.azaytsev.votingrestaurants.user.AuthUser;
import ru.azaytsev.votingrestaurants.model.Vote;
import ru.azaytsev.votingrestaurants.service.VoteService;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class VoteController {

    static final String REST_URL = "/api/user/votes";

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<Vote> createWithLocation(@AuthenticationPrincipal AuthUser authUser, @RequestParam Integer restaurantId) {
        Integer userId = authUser.id();
        log.info("create vote for restaurant id {} for user {}", restaurantId, userId);

        Vote created = voteService.create(userId, restaurantId, LocalDate.now());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser, @RequestParam Integer restaurantId) {
        int userId = authUser.id();
        voteService.update(userId, restaurantId, LocalDate.now(), LocalTime.now());
    }
}
