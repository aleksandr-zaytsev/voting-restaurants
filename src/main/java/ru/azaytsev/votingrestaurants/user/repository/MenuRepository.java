package ru.azaytsev.votingrestaurants.user.repository;

import org.springframework.stereotype.Repository;
import ru.azaytsev.votingrestaurants.common.BaseRepository;
import ru.azaytsev.votingrestaurants.user.model.Menu;
import ru.azaytsev.votingrestaurants.user.model.Restaurant;

@Repository
public interface MenuRepository extends BaseRepository<Menu> {

    Menu findMenuByRestaurantAndDayOfWeek(Restaurant restaurant, String dayOfWeek);
}
