package pl.edu.wszib.media.store.services;

import java.util.List;
import java.util.Optional;

import pl.edu.wszib.media.store.model.Trip;

public interface ITripService {
    void save(Trip trip);

    Optional<Trip> getById(Long id);

    void update(Trip trip, Long id);

    List<Trip> getAll();

    List<Trip> getByPattern(String pattern);
}
