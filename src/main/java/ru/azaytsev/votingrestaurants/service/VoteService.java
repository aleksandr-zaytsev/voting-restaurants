package ru.azaytsev.votingrestaurants.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.azaytsev.votingrestaurants.common.error.DataConflictException;
import ru.azaytsev.votingrestaurants.common.error.IllegalRequestDataException;
import ru.azaytsev.votingrestaurants.common.error.NotFoundException;
import ru.azaytsev.votingrestaurants.model.Restaurant;
import ru.azaytsev.votingrestaurants.model.User;
import ru.azaytsev.votingrestaurants.model.Vote;
import ru.azaytsev.votingrestaurants.repository.RestaurantRepository;
import ru.azaytsev.votingrestaurants.repository.UserRepository;
import ru.azaytsev.votingrestaurants.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class VoteService {

    private static final LocalTime VOTING_FINISH_TIME = LocalTime.of(11, 0);

    private final RestaurantRepository restaurantRepository;
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;

    @Transactional
    public Vote create(int userId, int restaurantId, LocalDate newVoteDate) {
        Restaurant restaurant = findRestUtil(restaurantId);

        User user = findUserUtil(userId);

        Vote vote = voteRepository.findByUserAndByDate(userId, LocalDate.now());
        if (vote != null) {
            throw new DataConflictException("Error: vote already exists");
        }

        vote = new Vote(user, restaurant, newVoteDate);
        return voteRepository.save(vote);
    }

    @Transactional
    public void update(int userId, int restaurantId, LocalDate newVoteDate, LocalTime newVoteTime) {

        Restaurant restaurant = findRestUtil(restaurantId);
        User user = findUserUtil(userId);

        Vote vote = voteRepository.findByUserAndByDate(userId, LocalDate.now());
        if (vote == null) {
            throw new IllegalRequestDataException("Vote does not exist");
        }

        if (isChanging(newVoteTime)) {
            vote = new Vote(user, restaurant, newVoteDate);
            voteRepository.save(vote);
        } else {
            throw new IllegalRequestDataException("You can't change your vote for today.");
        }
    }

    private Restaurant findRestUtil(int restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("Restaurant with id=" + restaurantId + " not found."));
    }

    private User findUserUtil(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id=" + userId + " not found."));
    }

    private static boolean isChanging(LocalTime newVoteTime) {
        return newVoteTime.isBefore(VOTING_FINISH_TIME);
    }
}
