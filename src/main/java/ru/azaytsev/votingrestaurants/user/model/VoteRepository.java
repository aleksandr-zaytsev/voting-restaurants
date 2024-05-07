package ru.azaytsev.votingrestaurants.user.model;

import org.springframework.stereotype.Repository;
import ru.azaytsev.votingrestaurants.common.BaseRepository;

import java.util.List;

@Repository
public interface VoteRepository extends BaseRepository<Vote> {

    List<Vote> findAll();
}
