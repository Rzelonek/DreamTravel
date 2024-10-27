package pl.edu.wszib.media.store.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.wszib.media.store.dao.impl.spring.data.UserDAO;
import pl.edu.wszib.media.store.model.User;
import pl.edu.wszib.media.store.services.IBalanceService;

@Service
@RequiredArgsConstructor
public class BalanceService implements IBalanceService {

      private final UserDAO userDAO; // Inject the UserDAO

      @Override
      public void updateBalance(User user, Double amount) {
            // Update the user's balance
            user.setBalance(user.getBalance() + amount);

            // Persist the updated user in the database using the UserDAO
            userDAO.save(user);
      }

      @Override
      public Double getBalance(User user) {
            // Fetch the user's balance (if necessary, could also retrieve the user)
            return user.getBalance();
      }
}
