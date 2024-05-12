package ru.azaytsev.votingrestaurants.user.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.azaytsev.votingrestaurants.common.error.DataConflictException;
import ru.azaytsev.votingrestaurants.user.model.Restaurant;
import ru.azaytsev.votingrestaurants.user.model.User;
import ru.azaytsev.votingrestaurants.user.model.Vote;
import ru.azaytsev.votingrestaurants.user.repository.RestaurantRepository;
import ru.azaytsev.votingrestaurants.user.repository.UserRepository;
import ru.azaytsev.votingrestaurants.user.repository.VoteRepository;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final RestaurantRepository restaurantRepository;
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;

    @Transactional
    public Vote addVote(Integer userId, Integer restaurantId, LocalDate newVoteDate) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant with id=" + restaurantId + " not found."));

        Vote vote = voteRepository.findByUserIdAndRestaurantIdForToday(userId, restaurantId, LocalDate.now());
        if (vote != null) {
            throw new DataConflictException("Error: vote already exists");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id=" + userId + " not found."));
        vote = new Vote();
        vote.setUser(user);
        vote.setRestaurant(restaurant);
        vote.setVoteDate(newVoteDate);
        return voteRepository.save(vote);
    }
    }
}
