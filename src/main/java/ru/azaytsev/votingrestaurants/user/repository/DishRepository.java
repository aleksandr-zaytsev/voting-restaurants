package ru.azaytsev.votingrestaurants.user.repository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.azaytsev.votingrestaurants.common.BaseRepository;
import ru.azaytsev.votingrestaurants.common.error.IllegalRequestDataException;
import ru.azaytsev.votingrestaurants.user.model.Dish;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {
}
