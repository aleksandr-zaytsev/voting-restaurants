package ru.azaytsev.votingrestaurants.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.azaytsev.votingrestaurants.model.Dish;
import ru.azaytsev.votingrestaurants.model.Menu;
import ru.azaytsev.votingrestaurants.repository.DishRepository;
import ru.azaytsev.votingrestaurants.repository.MenuRepository;
import ru.azaytsev.votingrestaurants.service.MenuService;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = MenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class MenuController {

    static final String REST_URL = "/api/admin/restaurants";

    private final MenuRepository menuRepository;
    private final MenuService menuService;
    private final DishRepository dishRepository;

    @PostMapping(value = "/{restaurantId}/menus",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createWithLocation(@RequestBody Menu menu,
                                                   @PathVariable Integer restaurantId) {
        log.info("create {} for restaurant {}", menu, restaurantId);

        if (menu.getMenuDate() == null) {
            menu.setMenuDate(LocalDate.now());
            log.info("set date {} for menu", menu.getMenuDate());
        }
        Menu createdMenu = menuService.create(menu, restaurantId);
        List<Dish> dishes = menu.getDishes();
        if (dishes != null) {
            dishes.forEach(dish -> dish.setMenu(createdMenu));
            dishRepository.saveAll(dishes);
        }

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{restaurantId}/menus/{menuId}")
                .buildAndExpand(restaurantId, createdMenu.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(createdMenu);
    }

    @PutMapping(value = "/{restaurantId}/menus/{menuId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Menu menu,
                       @PathVariable Integer restaurantId,
                       @PathVariable Integer menuId) {
        log.info("update menu {} for restaurant id {}", menu, restaurantId);
        menu.setId(menuId);

        List<Dish> dishes = menu.getDishes();
        if (dishes != null) {
            dishes.forEach(dish -> dish.setId(null));
        }
        menuService.update(menu, restaurantId);
    }

    @DeleteMapping("/{restaurantId}/menus/{menuId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer menuId, @PathVariable Integer restaurantId) {
        log.info("delete menu with id {} for restaurant with id {}", menuId, restaurantId);
        menuRepository.delete(menuId, restaurantId);
    }

    public MenuController(MenuRepository menuRepository, MenuService menuService, DishRepository dishRepository) {
        this.menuRepository = menuRepository;
        this.menuService = menuService;
        this.dishRepository = dishRepository;
    }
}
