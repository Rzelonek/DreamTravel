package pl.edu.wszib.media.store.dao.impl.hibernate;

import jakarta.persistence.NoResultException;
import pl.edu.wszib.media.store.dao.ITripDAO;
import pl.edu.wszib.media.store.model.Trip;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public class TripDAO implements ITripDAO {

    private final SessionFactory sessionFactory;

    public TripDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Trip> getById(Long id) {
        Session session = this.sessionFactory.openSession();
        Query<Trip> query = session.createQuery("FROM pl.edu.wszib.media.store.model.Trip WHERE id = :id", Trip.class);
        query.setParameter("id", id);

        try {
            Trip trip = query.getSingleResult();
            return Optional.of(trip);
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Trip> getAll() {
        Session session = this.sessionFactory.openSession();
        Query<Trip> query = session.createQuery("FROM pl.edu.wszib.media.store.model.Trip", Trip.class);
        List<Trip> trips = query.getResultList();
        session.close();
        return trips;
    }

    @Override
    public List<Trip> getByPattern(String pattern) {
        Session session = this.sessionFactory.openSession();
        Query<Trip> query = session.createQuery(
                "FROM pl.edu.wszib.media.store.model.Trip WHERE title LIKE :pattern OR direction LIKE :pattern",
                Trip.class);
        query.setParameter("pattern", "%" + pattern + "%");
        List<Trip> trips = query.getResultList();
        session.close();
        return trips;
    }

    @Override
    public void save(Trip trip) {
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.persist(trip);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void update(Trip trip) {
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.merge(trip);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void remove(Long id) {
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.remove(new Trip(id));
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}
