package ru.azaytsev.votingrestaurants.user.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.azaytsev.votingrestaurants.common.BaseRepository;
import ru.azaytsev.votingrestaurants.user.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    List<Vote> findAll();

    Vote findByUserId(Integer userId);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.restaurant.id=:restaurantId AND v.voteDate=:todayDate")
    Vote findByUserIdAndRestaurantIdForToday(int userId, int restaurantId, @Param("todayDate") LocalDate todayDate);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.voteDate=:todayDate")
    Optional<Vote> findByUserIdForToday(@Param("userId") int userId, @Param("todayDate") LocalDate todayDate);

    @Query("SELECT count(v) FROM Vote v WHERE v.restaurant.id = :restaurantId")
    int getCount(int restaurantId);
}
