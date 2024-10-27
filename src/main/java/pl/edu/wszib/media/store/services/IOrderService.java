package pl.edu.wszib.media.store.services;

import java.util.List;

import pl.edu.wszib.media.store.model.Order;

public interface IOrderService {
    void confirmOrder();

    List<Order> getOrdersForCurrentUser();
}
