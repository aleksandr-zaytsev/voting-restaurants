package ru.azaytsev.votingrestaurants.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.azaytsev.votingrestaurants.common.error.DataConflictException;
import ru.azaytsev.votingrestaurants.common.error.IllegalRequestDataException;
import ru.azaytsev.votingrestaurants.common.error.NotFoundException;
import ru.azaytsev.votingrestaurants.user.model.Restaurant;
import ru.azaytsev.votingrestaurants.user.model.User;
import ru.azaytsev.votingrestaurants.user.model.Vote;
import ru.azaytsev.votingrestaurants.user.repository.RestaurantRepository;
import ru.azaytsev.votingrestaurants.user.repository.UserRepository;
import ru.azaytsev.votingrestaurants.user.repository.VoteRepository;

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
    public Vote create(Integer userId, Integer restaurantId, LocalDate newVoteDate) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("Restaurant with id=" + restaurantId + " not found."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id=" + userId + " not found."));

        Vote vote = voteRepository.findByUserIdAndRestaurantIdForToday(userId, restaurantId, LocalDate.now());
        if (vote != null) {
            throw new DataConflictException("Error: vote already exists");
        }

        vote = new Vote();
        vote.setUser(user);
        vote.setRestaurant(restaurant);
        vote.setVoteDate(newVoteDate);
        return voteRepository.save(vote);
    }


    public void update(int userId, Integer restaurantId, LocalDate newVoteDate, LocalTime newVoteTime) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("Restaurant with id=" + restaurantId + " not found."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id=" + userId + " not found."));

        Vote vote = voteRepository.findByUserId(userId);
        if (vote == null) {
            return;
        }

        if (isChanging(newVoteTime)) {
            vote.setUser(user);
            vote.setRestaurant(restaurant);
            vote.setVoteDate(newVoteDate);
            voteRepository.save(vote);
        } else {
            throw new IllegalRequestDataException("Voting is closed for today.");
        }
    }

    private static boolean isChanging(LocalTime newVoteDate) {
        return newVoteDate.isBefore(VOTING_FINISH_TIME);
    }
}
