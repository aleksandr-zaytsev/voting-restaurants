package ru.azaytsev.votingrestaurants.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.azaytsev.votingrestaurants.common.BaseRepository;
import ru.azaytsev.votingrestaurants.model.Menu;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {

    @EntityGraph(attributePaths = {"dishes"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT m FROM Menu m")
    List<Menu> findAll();

    @Query("SELECT m FROM Menu m WHERE m.menuDate=:menuDate")
    List<Menu> getAllByMenuDate(@Param("menuDate") LocalDate menuDate);

    @Query("SELECT m FROM Menu m WHERE m.menuDate=:menuDate AND m.restaurant.id=:restaurantId")
    Menu getByMenuDateAndRestaurantId(@Param("menuDate") LocalDate menuDate, @Param("restaurantId") int restaurantId);
}