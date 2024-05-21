package ru.azaytsev.votingrestaurants.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.azaytsev.votingrestaurants.model.Menu;
import ru.azaytsev.votingrestaurants.model.Restaurant;
import ru.azaytsev.votingrestaurants.repository.MenuRepository;
import ru.azaytsev.votingrestaurants.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class CommonController {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    @GetMapping("/restaurants")
    public List<Restaurant> getAll() {
        log.info("getAll");
        return restaurantRepository.findAll(Sort.by("name"));
    }

    @GetMapping("/menus")
    public List<Menu> getAllForToday() {
        LocalDate menuDate = LocalDate.now();
        log.info("get all menus for today {}", menuDate);
        return menuRepository.getAllByMenuDate(menuDate);
    }

    @GetMapping("/menus/{id}")
    public Menu get(@PathVariable int id) {
        log.info("get menu with dishes by id {}", id);
        return menuRepository.getWithDishes(id);
    }
}