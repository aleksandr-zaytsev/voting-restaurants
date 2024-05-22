package ru.azaytsev.votingrestaurants.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.azaytsev.votingrestaurants.common.error.DataConflictException;
import ru.azaytsev.votingrestaurants.model.Menu;
import ru.azaytsev.votingrestaurants.repository.MenuRepository;
import ru.azaytsev.votingrestaurants.service.MenuService;

import java.net.URI;
import java.time.LocalDate;

@RestController
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class AdminMenuController {

    static final String REST_URL = "/api/admin/restaurants";

    private final MenuRepository menuRepository;
    private final MenuService menuService;

    @PostMapping(value = "/{restaurantId}/menus",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createWithLocation(@RequestBody Menu menu,
                                                   @Valid @PathVariable Integer restaurantId) {
        log.info("create {} for restaurant {}", menu, restaurantId);

        if (menu.getMenuDate() == null) {
            menu.setMenuDate(LocalDate.now());
            log.info("set date {} for menu", menu.getMenuDate());
        }
        Menu createdMenu = menuService.create(menu, restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{restaurantId}/menus/{menuDate}")
                .buildAndExpand(restaurantId, createdMenu.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(createdMenu);
    }

    @PutMapping(value = "/{restaurantId}/menus/{menuDate}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Menu menu,
                       @PathVariable Integer restaurantId, @PathVariable LocalDate menuDate
    ) {
        log.info("update menu {} for restaurant id {} and date {}", menu, restaurantId, menuDate);
        menuService.update(menu, restaurantId, menuDate);
    }

    @DeleteMapping("/{restaurantId}/menus/{menuDate}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable LocalDate menuDate, @PathVariable Integer restaurantId) {
        log.info("delete menu with date {} for restaurant with id {}", menuDate, restaurantId);
        Menu menuExisted = menuRepository.getByMenuDateAndRestaurantId(menuDate, restaurantId);
        if (menuExisted == null) {
            throw new DataConflictException("Menu does not exist");
        }
        menuRepository.delete(menuExisted);
    }
}