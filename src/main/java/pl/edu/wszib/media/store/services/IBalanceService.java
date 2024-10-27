package pl.edu.wszib.media.store.services;

import pl.edu.wszib.media.store.model.User;

public interface IBalanceService {
      void updateBalance(User user, Double amount);

      Double getBalance(User user);
}
