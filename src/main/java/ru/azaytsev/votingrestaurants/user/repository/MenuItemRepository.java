package ru.azaytsev.votingrestaurants.user.repository;

import org.springframework.stereotype.Repository;
import ru.azaytsev.votingrestaurants.common.BaseRepository;
import ru.azaytsev.votingrestaurants.user.model.Menu;
import ru.azaytsev.votingrestaurants.user.model.MenuItem;

import java.util.List;

@Repository
public interface MenuItemRepository extends BaseRepository<MenuItem> {
    List<MenuItem> findAll();

    List<MenuItem> findByMenu(Menu menu);

    default MenuItem create(MenuItem menuItem) {
        return save(menuItem);
    }
}
