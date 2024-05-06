package ru.azaytsev.votingrestaurants.user.model;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.azaytsev.votingrestaurants.common.BaseRepository;

import java.util.List;

@Repository
public interface VoteRepository extends BaseRepository<Vote> {

    default List<Vote> findAll() {
        return findAll(Sort.by("name"));
    }

    default Vote create(Vote vote) {
        return save(vote);
    }
}
