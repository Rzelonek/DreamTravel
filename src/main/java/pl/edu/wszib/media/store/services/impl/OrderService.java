package pl.edu.wszib.media.store.services.impl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import pl.edu.wszib.media.store.dao.ITripDAO;
import pl.edu.wszib.media.store.dao.IOrderDAO;
import pl.edu.wszib.media.store.dao.impl.spring.data.TripDAO;
import pl.edu.wszib.media.store.dao.impl.spring.data.OrderDAO;
import pl.edu.wszib.media.store.dao.impl.spring.data.UserDAO;
import pl.edu.wszib.media.store.exceptions.EmptyCartException;
import pl.edu.wszib.media.store.exceptions.IncorrectCartPositionsException;
import pl.edu.wszib.media.store.exceptions.InsufficientBalanceException;
import pl.edu.wszib.media.store.exceptions.UserNotLoggedException;
import pl.edu.wszib.media.store.model.Trip;
import pl.edu.wszib.media.store.model.Order;
import pl.edu.wszib.media.store.model.Position;
import pl.edu.wszib.media.store.model.User;
import pl.edu.wszib.media.store.services.IOrderService;
import pl.edu.wszib.media.store.session.SessionConstants;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final HttpSession httpSession;

    private final TripDAO tripDAO;
    private final OrderDAO orderDAO;
    private final UserDAO userDAO;

    @Override
    public void confirmOrder() {
        Set<Position> cart = (Set<Position>) this.httpSession.getAttribute(SessionConstants.CART_KEY);
        if (cart == null || cart.isEmpty()) {
            throw new EmptyCartException();
        }
        List<Position> toRemove = cart.stream().filter(p -> {
            Optional<Trip> tripBox = this.tripDAO.findById(p.getTrip().getId());
            return tripBox.isEmpty() || tripBox.get().getQuantity() < p.getQuantity();
        }).toList();

        if (!toRemove.isEmpty()) {
            toRemove.forEach(cart::remove);
            throw new IncorrectCartPositionsException();
        }

        Order order = new Order();
        order.setDate(LocalDateTime.now());
        order.setUser((User) this.httpSession.getAttribute(SessionConstants.USER_KEY));
        order.setStatus(Order.Status.NEW);
        order.getPositions().addAll(cart);
        order.setSum(order.getPositions().stream()
                .mapToDouble(p -> p.getQuantity() * p.getTrip().getPrice()).sum());

        User user = (User) this.httpSession.getAttribute(SessionConstants.USER_KEY);
        if (user == null) {
            throw new UserNotLoggedException();
        }

        // Calculate the total order sum
        double orderSum = cart.stream()
                .mapToDouble(p -> p.getQuantity() * p.getTrip().getPrice()).sum();

        // Check if user has sufficient balance
        if (user.getBalance() < orderSum) {
            throw new InsufficientBalanceException("Insufficient balance for this order.");
        }

        // Deduct the order amount from the user's balance
        user.setBalance(user.getBalance() - orderSum);
        this.userDAO.save(user);

        order.getPositions().forEach(p -> {
            this.tripDAO.findById(p.getTrip().getId()).ifPresent(
                    trip -> {
                        trip.setQuantity(trip.getQuantity() - p.getQuantity());
                        this.tripDAO.save(trip);
                    });
        });

        this.orderDAO.save(order);
        cart.clear();
    }

    @Override
    public List<Order> getOrdersForCurrentUser() {
        User user = (User) this.httpSession.getAttribute(SessionConstants.USER_KEY);

        if (user == null) {
            throw new UserNotLoggedException();
        }

        // Check if the user is an admin
        if (user.getRole() == User.Role.ADMIN) {
            // Admin user sees all orders
            List<Order> orders = new ArrayList<>();
            this.orderDAO.findAll().forEach(orders::add);
            return orders;
        } else {
            // Regular user sees only their own orders
            return new ArrayList<>(this.userDAO.findById(user.getId()).get().getOrders());
        }
    }

}
