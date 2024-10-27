package pl.edu.wszib.media.store.services.impl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import pl.edu.wszib.media.store.dao.ITripDAO;
import pl.edu.wszib.media.store.dao.impl.spring.data.TripDAO;
import pl.edu.wszib.media.store.model.Trip;
import pl.edu.wszib.media.store.model.Position;
import pl.edu.wszib.media.store.services.ICartService;
import pl.edu.wszib.media.store.session.SessionConstants;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final TripDAO tripDAO;
    private final HttpSession httpSession;

    public double calculateCartSum() {
        Set<Position> cart = (Set<Position>) this.httpSession.getAttribute(SessionConstants.CART_KEY);
        if (cart == null) {
            return 0.0;
        }
        return cart.stream().mapToDouble(p -> p.getQuantity() * p.getTrip().getPrice()).sum();
    }

    @Override
    public void addTripToCart(Long id) {
        Optional<Trip> tripBox = this.tripDAO.findById(id);

        tripBox.ifPresent(trip -> {
            final Set<Position> cart = (Set<Position>) this.httpSession.getAttribute(SessionConstants.CART_KEY);
            Optional<Position> alreadyTripPosition = cart.stream()
                    .filter(p -> p.getTrip().getId().equals(id))
                    .findFirst();

            alreadyTripPosition.ifPresentOrElse(Position::incrementQuantity,
                    () -> cart.add(new Position(null, trip, 1)));
        });
    }
}
