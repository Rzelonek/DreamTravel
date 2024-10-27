package pl.edu.wszib.media.store.services.impl;

import lombok.RequiredArgsConstructor;
import pl.edu.wszib.media.store.dao.ITripDAO;
import pl.edu.wszib.media.store.dao.impl.spring.data.TripDAO;
import pl.edu.wszib.media.store.model.Trip;
import pl.edu.wszib.media.store.services.ITripService;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TripService implements ITripService {

    private final TripDAO tripDAO;

    @Override
    public void save(Trip trip) {
        this.tripDAO.save(trip);
    }

    @Override
    public Optional<Trip> getById(Long id) {
        return this.tripDAO.findById(id);
    }

    @Override
    public void update(Trip trip, Long id) {
        trip.setId(id);
        this.tripDAO.save(trip);
    }

    @Override
    public List<Trip> getAll() {
        List<Trip> list = new ArrayList<>();
        this.tripDAO.findAll().forEach(list::add);
        return list;
    }

    @Override
    public List<Trip> getByPattern(String pattern) {
        return this.tripDAO.findByTitleLikeOrDirectionLike(pattern);
    }
}
