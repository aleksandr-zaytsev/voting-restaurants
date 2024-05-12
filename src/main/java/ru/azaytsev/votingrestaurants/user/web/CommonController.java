package ru.azaytsev.votingrestaurants.user.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.azaytsev.votingrestaurants.user.model.Menu;
import ru.azaytsev.votingrestaurants.user.repository.MenuRepository;
import ru.azaytsev.votingrestaurants.user.service.MenuService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class CommonController {

    private final MenuRepository menuRepository;

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

    public CommonController(MenuRepository menuRepository, MenuService menuService) {
        this.menuRepository = menuRepository;
    }
}