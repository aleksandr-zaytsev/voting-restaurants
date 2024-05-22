package ru.azaytsev.votingrestaurants.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.azaytsev.votingrestaurants.common.error.DataConflictException;
import ru.azaytsev.votingrestaurants.config.AppConfig;
import ru.azaytsev.votingrestaurants.model.Restaurant;
import ru.azaytsev.votingrestaurants.repository.RestaurantRepository;

import java.util.List;

import static ru.azaytsev.votingrestaurants.common.validation.ValidationUtil.assureIdConsistent;
import static ru.azaytsev.votingrestaurants.common.validation.ValidationUtil.checkNew;

@Service
@RequiredArgsConstructor

public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Transactional
    @CacheEvict(value = AppConfig.RESTAURANTS_CACHE, allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        checkNew(restaurant);
        if (restaurantRepository.findByName(restaurant.getName()) != null) {
            throw new DataConflictException("Restaurant already exists");
        }
        return restaurantRepository.save(restaurant);
    }

    @Transactional
    @CacheEvict(value = AppConfig.RESTAURANTS_CACHE, allEntries = true)
    public void update(Restaurant restaurant, int id) {
        assureIdConsistent(restaurant, id);
        restaurantRepository.save(restaurant);
    }

    @Cacheable(value = AppConfig.RESTAURANTS_CACHE)
    public List<Restaurant> findAll() {
        return restaurantRepository.findAll(Sort.by("name"));
    }

    @Cacheable(value = AppConfig.RESTAURANTS_CACHE)
    public Restaurant get(int id) {
        return restaurantRepository.getExisted(id);
    }
}