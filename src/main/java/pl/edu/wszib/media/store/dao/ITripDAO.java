package pl.edu.wszib.media.store.dao;

import java.util.List;
import java.util.Optional;

import pl.edu.wszib.media.store.model.Trip;

public interface ITripDAO {
    Optional<Trip> getById(Long id);

    List<Trip> getAll();

    List<Trip> getByPattern(String pattern);

    void save(Trip trip);

    void update(Trip trip);

    void remove(Long id);
}
