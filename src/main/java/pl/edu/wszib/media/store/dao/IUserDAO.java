package pl.edu.wszib.media.store.dao;

import java.util.List;
import java.util.Optional;

import pl.edu.wszib.media.store.model.User;

public interface IUserDAO {
    Optional<User> getById(Long id);

    Optional<User> getByLogin(String login);

    List<User> getAll();

    void save(User user);

    void remove(Long id);

    void update(User user);
}
