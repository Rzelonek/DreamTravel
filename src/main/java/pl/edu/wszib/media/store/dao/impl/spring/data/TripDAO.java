package pl.edu.wszib.media.store.dao.impl.spring.data;

import org.springframework.data.repository.CrudRepository;

import pl.edu.wszib.media.store.model.Trip;

import java.util.List;

public interface TripDAO extends CrudRepository<Trip, Long> {
    List<Trip> findByTitleLikeOrDirectionLike(String pattern1, String pattern2);

    default List<Trip> findByTitleLikeOrDirectionLike(String pattern) {
        return findByTitleLikeOrDirectionLike(pattern, pattern);
    }
}
