package ru.azaytsev.votingrestaurants.user.service;

import org.springframework.stereotype.Service;
import ru.azaytsev.votingrestaurants.user.to.MenuTo;
import ru.azaytsev.votingrestaurants.user.to.RestaurantListTo;
import ru.azaytsev.votingrestaurants.user.to.RestaurantTo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantMenuService {


    public Optional<RestaurantListTo> getAll() {

        Optional<RestaurantListTo> list = null;
        MenuTo m1 = new MenuTo("fish1", new BigDecimal(100.00));
        MenuTo m2 = new MenuTo("fish2", new BigDecimal(150.00));

        List<MenuTo> menuToList = new ArrayList<>();
        menuToList.add(m1);
        menuToList.add(m2);
        System.out.println(menuToList);
        RestaurantTo r1 = new RestaurantTo("1", 1, menuToList);
        RestaurantListTo restaurantListTo = new RestaurantListTo(List.of(r1));
        return Optional.of(restaurantListTo);
    }
}
