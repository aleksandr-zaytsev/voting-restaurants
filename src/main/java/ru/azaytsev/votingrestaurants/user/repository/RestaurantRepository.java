package ru.azaytsev.votingrestaurants.user.repository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.azaytsev.votingrestaurants.common.BaseRepository;
import ru.azaytsev.votingrestaurants.common.error.IllegalRequestDataException;
import ru.azaytsev.votingrestaurants.user.model.Restaurant;

import java.util.List;

@Repository
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    default List<Restaurant> findAll() {
        return findAll(Sort.by("name"));
    }

    default Restaurant create(Restaurant restaurant) {
        return save(restaurant);
    }

    @Transactional
    default Restaurant update(int id, Restaurant restaurant) {
        checkEntityExist(existsById(id), id, Restaurant.class);
        restaurant.setId(id);
        return save(restaurant);
    }

    public static <T> void checkEntityExist(boolean exist, int id, Class<T> clazz) {
        if (!exist) {
            throw new IllegalRequestDataException(String.format("%s with id = %d not found", clazz.getSimpleName(), id));
        }
    }
}