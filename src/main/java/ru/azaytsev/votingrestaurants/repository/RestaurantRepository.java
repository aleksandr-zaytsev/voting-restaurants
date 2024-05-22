package ru.azaytsev.votingrestaurants.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.azaytsev.votingrestaurants.common.BaseRepository;
import ru.azaytsev.votingrestaurants.model.Restaurant;

import java.util.List;
@Repository
@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Query("SELECT r FROM Restaurant r")
    List<Restaurant> findAll();

    @Query("SELECT r FROM Restaurant r WHERE r.name=:name")
    Restaurant findByName(@Param("name") String name);
}