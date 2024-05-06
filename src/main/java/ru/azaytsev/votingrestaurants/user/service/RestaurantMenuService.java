package ru.azaytsev.votingrestaurants.user.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.azaytsev.votingrestaurants.user.model.Dish;
import ru.azaytsev.votingrestaurants.user.model.Menu;
import ru.azaytsev.votingrestaurants.user.model.MenuItem;
import ru.azaytsev.votingrestaurants.user.model.Restaurant;
import ru.azaytsev.votingrestaurants.user.repository.DishRepository;
import ru.azaytsev.votingrestaurants.user.repository.MenuItemRepository;
import ru.azaytsev.votingrestaurants.user.repository.MenuRepository;
import ru.azaytsev.votingrestaurants.user.repository.RestaurantRepository;
import ru.azaytsev.votingrestaurants.user.to.MenuTo;
import ru.azaytsev.votingrestaurants.user.to.RestaurantListTo;
import ru.azaytsev.votingrestaurants.user.to.RestaurantTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RestaurantMenuService {

    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;
    private final DishRepository dishRepository;

    public Optional<RestaurantListTo> getAll() {


        List<RestaurantTo> list = null;

        List<Restaurant> restaurantList = restaurantRepository.findAll();

        for (Restaurant restaurant : restaurantList) {
            Menu menu = menuRepository.findByIdAndLocalDate(restaurant.id(), LocalDate.now());

            List<MenuItem> menuItemList = menuItemRepository.findByMenu(menu);
            List<Dish> dishList = dishRepository.findAll();
            List<MenuTo> menuToList = null;

            for (var menuItem : menuItemList) {
                Dish localDish = dishList.stream().filter(it -> it.getId() == menuItem.getDish().getId()).findFirst().orElseThrow();
                menuToList.add(new MenuTo(localDish.getName(), menuItem.getPrice()));
            }

            RestaurantTo restaurantTo = new RestaurantTo(restaurant.getName(), 1, menuToList);
            list.add(restaurantTo);
        }

        RestaurantListTo restaurantListTo = new RestaurantListTo(list);
        return Optional.of(restaurantListTo);
    }
}
