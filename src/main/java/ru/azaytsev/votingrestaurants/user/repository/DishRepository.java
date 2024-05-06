package ru.azaytsev.votingrestaurants.user.repository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.azaytsev.votingrestaurants.common.BaseRepository;
import ru.azaytsev.votingrestaurants.common.error.IllegalRequestDataException;
import ru.azaytsev.votingrestaurants.user.model.Dish;

import java.util.List;

@Repository
public interface DishRepository extends BaseRepository<Dish> {

    default List<Dish> findAll() {
        return findAll(Sort.by("name"));
    }

    default Dish create(Dish dish) {
        return save(dish);
    }

    default Dish update(int id, Dish dish) {
        checkEntityExist(existsById(id), id, Dish.class);
        dish.setId(id);
        return save(dish);
    }

    public static <T> void checkEntityExist(boolean exist, int id, Class<T> clazz) {
        if (!exist) {
            throw new IllegalRequestDataException(String.format("%s with id = %d not found", clazz.getSimpleName(), id));
        }

    }
}
