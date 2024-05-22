package ru.azaytsev.votingrestaurants.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.azaytsev.votingrestaurants.model.Menu;
import ru.azaytsev.votingrestaurants.service.MenuService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/user/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class UserMenuController {

    private final MenuService menuService;

    @GetMapping("/menus")
    public List<Menu> getAllForToday() {
        LocalDate menuDate = LocalDate.now();
        log.info("get all menus for today {}", menuDate);
        return menuService.getAllByDate(menuDate);
    }

    @GetMapping("/{restaurantId}/menus/{menuDate}")
    public Menu get(@PathVariable int restaurantId, @PathVariable LocalDate menuDate) {
        log.info("get menu with dishes by restaurant id {} and date{} ", restaurantId, menuDate);
        return menuService.get(restaurantId, menuDate);
    }
}
