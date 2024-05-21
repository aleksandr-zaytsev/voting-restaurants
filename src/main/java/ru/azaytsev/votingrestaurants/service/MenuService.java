package ru.azaytsev.votingrestaurants.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.azaytsev.votingrestaurants.common.error.DataConflictException;
import ru.azaytsev.votingrestaurants.model.Menu;
import ru.azaytsev.votingrestaurants.repository.MenuRepository;
import ru.azaytsev.votingrestaurants.repository.RestaurantRepository;

import static ru.azaytsev.votingrestaurants.common.validation.ValidationUtil.checkNew;

@Service
@AllArgsConstructor
public class MenuService {

    protected MenuRepository menuRepository;

    protected RestaurantRepository restaurantRepository;

    @Transactional
    public Menu create(Menu menu, int restaurantId) {
        checkNew(menu);
        if (!menuRepository.getAllByMenuDateAndRestaurantId(menu.getMenuDate(), restaurantId).isEmpty()) {
            throw new DataConflictException("Menu already exist");
        }

        menu.setRestaurant(restaurantRepository.getExisted(restaurantId));
        return menuRepository.save(menu);
    }

    public void update(Menu menu, int restaurantId) {
        menu.setRestaurant((restaurantRepository.getExisted(restaurantId)));
        menuRepository.save(menu);
    }

    public Menu get(int id, int restaurantId) {
        return menuRepository.findById(id)
                .filter(menu -> menu.getRestaurant().getId() == restaurantId)
                .orElse(null);
    }
}
