package ru.azaytsev.votingrestaurants.service;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.azaytsev.votingrestaurants.common.error.DataConflictException;
import ru.azaytsev.votingrestaurants.config.AppConfig;
import ru.azaytsev.votingrestaurants.model.Dish;
import ru.azaytsev.votingrestaurants.model.Menu;
import ru.azaytsev.votingrestaurants.repository.DishRepository;
import ru.azaytsev.votingrestaurants.repository.MenuRepository;
import ru.azaytsev.votingrestaurants.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.azaytsev.votingrestaurants.common.validation.ValidationUtil.checkNew;

@Service
@AllArgsConstructor
public class MenuService {

    protected MenuRepository menuRepository;
    protected RestaurantRepository restaurantRepository;
    protected DishRepository dishRepository;

    @Transactional
    @CacheEvict(value = AppConfig.MENUS_CACHE, allEntries = true)
    public Menu create(Menu menu, int restaurantId) {
        checkNew(menu);
        if (menuRepository.getByMenuDateAndRestaurantId(menu.getMenuDate(), restaurantId) != null) {
            throw new DataConflictException("Menu already exists");
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
    @CacheEvict(value = AppConfig.MENUS_CACHE, allEntries = true)
    public void update(Menu menu, int restaurantId, LocalDate menuDate) {
        Menu menuExisted = menuRepository.getByMenuDateAndRestaurantId(menuDate, restaurantId);
        if (menuExisted == null) {
            throw new DataConflictException("Menu does not exist");
        }

        List<Dish> dishes = menu.getDishes();
        List<Dish> listDishExists = dishRepository.findDishesByMenuId(menuExisted.id());
        for (Dish dish : dishes) {
            dish.setMenu(menuExisted);

            listDishExists.stream()
                    .filter(dishExist ->
                            dishExist.getName().equals(dish.getName())).findFirst().ifPresent(dishExists -> dish.setId(dishExists.getId()));

        }
        dishRepository.saveAll(dishes);
    }

    @Cacheable(value = AppConfig.MENUS_CACHE)
    public List<Menu> getAllByDate(LocalDate menuDate) {
        return menuRepository.getAllByMenuDate(menuDate);
    }

    @Cacheable(value = AppConfig.MENUS_CACHE)
    public Menu get(int restaurantId, LocalDate menuDate) {
        Menu menuExisted = menuRepository.getByMenuDateAndRestaurantId(menuDate, restaurantId);
        if (menuExisted == null) {
            throw new DataConflictException("Menu does not exist");
        }
        return menuExisted;
    }
}