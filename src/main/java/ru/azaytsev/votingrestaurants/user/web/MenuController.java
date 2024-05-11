package ru.azaytsev.votingrestaurants.user.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.azaytsev.votingrestaurants.user.model.Dish;
import ru.azaytsev.votingrestaurants.user.model.Menu;
import ru.azaytsev.votingrestaurants.user.repository.MenuRepository;
import ru.azaytsev.votingrestaurants.user.repository.RestaurantRepository;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = MenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class MenuController {

    static final String REST_URL = "/api/admin/restaurants";


    protected MenuRepository menuRepository;
    protected RestaurantRepository restaurantRepository;

    //TODO
    @GetMapping("/menus")
    public List<Menu> getAll() {
        log.info("getAll");
        return menuRepository.findAll();
    }

    @PostMapping(value = "/{restaurantId}/menus",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createWithLocation(Menu menu, Integer restaurantId) {
        log.info("create {} for restaurant {}", menu, restaurantId);

        List<Dish> dishes = menu.getDishes();
        if (dishes != null) {
            dishes.forEach(dish -> dish.setId(null));
        }
        if (menu.getMenuDate() == null) {
            menu.setMenuDate(LocalDate.now());
            log.info("set date {} for menu", menu.getMenuDate());
        }
        Menu created = menuRepository.save(menu);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{restaurantId}/menus/{menuId}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{restaurantId}/menus/{menuId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(Menu menu, Integer restaurantId, Integer menuId) {
        log.info("update menu {} for restaurant {}", menu, restaurantId);
        menu.setId(menuId);
        //Fix bug with lost dishes if dish_id not null (dishes always new, previous will delete)
        List<Dish> dishes = menu.getDishes();
        if (dishes != null) {
            dishes.forEach(dish -> dish.setId(null));
        }
        menu.setRestaurant(restaurantRepository.getOne(restaurantId));
        menuRepository.save(menu);
    }
}
