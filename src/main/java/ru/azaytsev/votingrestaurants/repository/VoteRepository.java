package ru.azaytsev.votingrestaurants.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.azaytsev.votingrestaurants.common.BaseRepository;
import ru.azaytsev.votingrestaurants.model.Vote;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.voteDate=:todayDate")
    Vote findByUserIdForToday(@Param("userId") int userId, @Param("todayDate") LocalDate todayDate);

    @Query("SELECT v FROM Vote v WHERE v.voteDate=:voteDate ORDER BY v.restaurant.name")
    List<Vote> findAllByVoteDate(@Param("voteDate") LocalDate voteDate);

    @Query("SELECT count(v) FROM Vote v WHERE v.restaurant.id = :restaurantId AND v.voteDate = :voteDate")
    int getCountByRestaurantForToday(@Param("restaurantId") int restaurantId, @Param("voteDate") LocalDate voteDate);
}