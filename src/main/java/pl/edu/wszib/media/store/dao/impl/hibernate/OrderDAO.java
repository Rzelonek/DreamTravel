package pl.edu.wszib.media.store.dao.impl.hibernate;

import jakarta.persistence.NoResultException;
import pl.edu.wszib.media.store.dao.IOrderDAO;
import pl.edu.wszib.media.store.model.Order;
import pl.edu.wszib.media.store.model.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class OrderDAO implements IOrderDAO {

    private final SessionFactory sessionFactory;
    private final UserDAO userDAO;

    public OrderDAO(SessionFactory sessionFactory, UserDAO userDAO) {
        this.sessionFactory = sessionFactory;
        this.userDAO = userDAO;
    }

    @Override
    public void persist(Order order) {
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.persist(order);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Order> getOrderByUserId(Long userId) {
        Optional<User> userBox = this.userDAO.getById(userId);
        return new ArrayList<>(userBox.map(user -> user.getOrders()).orElse(Collections.emptySet()));
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        Session session = this.sessionFactory.openSession();
        Query<Order> query = session.createQuery("FROM pl.edu.wszib.media.store.model.Order WHERE id = :id",
                Order.class);
        query.setParameter("id", id);
        try {
            Order order = query.getSingleResult();
            return Optional.of(order);
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            session.close();
        }
    }

    // New method to retrieve all orders
    @Override
    public List<Order> findAll() {
        Session session = this.sessionFactory.openSession();
        try {
            Query<Order> query = session.createQuery("FROM pl.edu.wszib.media.store.model.Order", Order.class);
            return query.list();
        } finally {
            session.close();
        }
    }

}
