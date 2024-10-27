package pl.edu.wszib.media.store.dao.impl.spring.data;

import org.springframework.data.repository.CrudRepository;

import pl.edu.wszib.media.store.model.Order;

public interface OrderDAO extends CrudRepository<Order, Long> {
}
