package pl.edu.wszib.media.store.dao.impl.spring.data;

import org.springframework.data.repository.CrudRepository;

import pl.edu.wszib.media.store.model.User;

import java.util.Optional;

public interface UserDAO extends CrudRepository<User, Long> {

    Optional<User> findByLogin(String login);
}
