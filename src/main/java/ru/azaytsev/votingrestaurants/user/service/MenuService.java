package ru.azaytsev.votingrestaurants.user.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.azaytsev.votingrestaurants.user.model.Menu;
import ru.azaytsev.votingrestaurants.user.repository.MenuRepository;
import ru.azaytsev.votingrestaurants.user.repository.RestaurantRepository;

import static ru.azaytsev.votingrestaurants.common.validation.ValidationUtil.checkNew;

@Service
@AllArgsConstructor
public class MenuService {

    protected MenuRepository menuRepository;

    protected RestaurantRepository restaurantRepository;

    @Transactional
    public Menu create(Menu menu, Integer restaurantId) {
        checkNew(menu);
        if (!menu.isNew() && get(menu.getId(), restaurantId) == null) {
            return null;
        }
        if ((menu.getMenuDate() == null) && !(menu.isNew())) {
            Menu previous = get(menu.getId(), restaurantId);
            menu.setMenuDate(previous.getMenuDate());
        }
        menu.setRestaurant(restaurantRepository.getExisted(restaurantId));
        return menuRepository.save(menu);
    }

    public void update(Menu menu, Integer restaurantId) {
        menu.setRestaurant((restaurantRepository.getExisted(restaurantId)));
        menuRepository.save(menu);
    }

    public Menu get(int id, int restaurantId) {
        return menuRepository.findById(id)
                .filter(menu -> menu.getRestaurant().getId() == restaurantId)
                .orElse(null);
    }
}
