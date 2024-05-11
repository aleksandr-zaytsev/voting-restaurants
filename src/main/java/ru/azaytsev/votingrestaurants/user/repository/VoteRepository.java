package ru.azaytsev.votingrestaurants.user.repository;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.azaytsev.votingrestaurants.common.BaseRepository;
import ru.azaytsev.votingrestaurants.user.model.Vote;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VoteRepository extends BaseRepository<Vote> {

    List<Vote> findAll();

    Vote findByUserId(Long userId);

    Vote getByUserIdAndVoteDate(@Param("userId") int userId, @Param("voteDate") LocalDate voteDate);


}
