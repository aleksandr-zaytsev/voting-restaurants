package ru.azaytsev.votingrestaurants.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.azaytsev.votingrestaurants.common.BaseRepository;
import ru.azaytsev.votingrestaurants.model.Dish;

public interface DishRepository extends BaseRepository<Dish> {

    @Query("SELECT d FROM Dish d WHERE d.name=:name AND d.menu.id=:menuId")
    Dish findByNameAndMenuId(@Param("name") String name, @Param("menuId") int menuId);
}

