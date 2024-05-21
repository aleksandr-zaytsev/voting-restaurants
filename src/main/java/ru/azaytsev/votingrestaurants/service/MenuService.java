package ru.azaytsev.votingrestaurants.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.azaytsev.votingrestaurants.common.error.DataConflictException;
import ru.azaytsev.votingrestaurants.model.Dish;
import ru.azaytsev.votingrestaurants.model.Menu;
import ru.azaytsev.votingrestaurants.repository.DishRepository;
import ru.azaytsev.votingrestaurants.repository.MenuRepository;
import ru.azaytsev.votingrestaurants.repository.RestaurantRepository;

import java.util.List;

import static ru.azaytsev.votingrestaurants.common.validation.ValidationUtil.checkNew;

@Service
@AllArgsConstructor
public class MenuService {

    protected MenuRepository menuRepository;

    protected RestaurantRepository restaurantRepository;
    protected DishRepository dishRepository;

    @Transactional
    public Menu create(Menu menu, int restaurantId) {
        checkNew(menu);
        if (menuRepository.getByMenuDateAndRestaurantId(menu.getMenuDate(), restaurantId) != null) {
            throw new DataConflictException("Menu already exist");
        }
        menu.setRestaurant(restaurantRepository.getExisted(restaurantId));
        menuRepository.save(menu);
        List<Dish> dishes = menu.getDishes();
        if (dishes != null) {
            dishes.forEach(dish -> dish.setMenu(menu));
            dishRepository.saveAll(dishes);
        }
        return menu;
    }

    @Transactional
    public void update(Menu menu, int restaurantId) {

        Menu menuExisted = menuRepository.getByMenuDateAndRestaurantId(menu.getMenuDate(), restaurantId);
        if (menuExisted == null) {
            throw new DataConflictException("Menu does not exist");
        }

        List<Dish> dishes = menu.getDishes();
        for (Dish dish : dishes) {
            dish.setMenu(menuExisted);
            Dish dishExists = dishRepository.findByNameAndMenuId(dish.getName(), menuExisted.id());
            if (dishExists != null) {
                dish.setId(dishExists.getId());
            }
        }
        dishRepository.saveAll(dishes);
    }

    public Menu get(int id, int restaurantId) {
        return menuRepository.findById(id)
                .filter(menu -> menu.getRestaurant().getId() == restaurantId)
                .orElse(null);
    }
}