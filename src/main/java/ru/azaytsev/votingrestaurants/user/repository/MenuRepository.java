package ru.azaytsev.votingrestaurants.user.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.azaytsev.votingrestaurants.common.BaseRepository;
import ru.azaytsev.votingrestaurants.user.model.Menu;
import ru.azaytsev.votingrestaurants.user.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {

    @Query("SELECT m FROM Menu m")
    List<Menu> findAll();

    @Query("SELECT m FROM Menu m WHERE m.menuDate=:menuDate ORDER BY m.restaurant.name")
    List<Menu> getAllByMenuDate(@Param("menuDate") LocalDate menuDate);

    @Query("SELECT m FROM Menu m WHERE m.id=?1")
    Menu getWithDishes(int id);

}
