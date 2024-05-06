package ru.azaytsev.votingrestaurants.user.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.azaytsev.votingrestaurants.common.BaseRepository;
import ru.azaytsev.votingrestaurants.user.model.Menu;

import java.time.LocalDate;

@Repository
public interface MenuRepository extends BaseRepository<Menu> {


    @Query(nativeQuery = true, value = "SELECT * " +
            "FROM Menu m " +
            "WHERE m.restaurant_id = :id " +
            "and m.DATE = :date")
    Menu findByIdAndLocalDate(Integer id, LocalDate date);
}
