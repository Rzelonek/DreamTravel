package pl.edu.wszib.media.store.dao;

import java.util.List;
import java.util.Optional;

import pl.edu.wszib.media.store.model.Order;

public interface IOrderDAO {
    void persist(Order order);

    List<Order> getOrderByUserId(Long userId);

    Optional<Order> getOrderById(Long id);

    List<Order> findAll();
}
