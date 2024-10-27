package pl.edu.wszib.media.store.dao.impl.memory;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.DigestUtils;

import pl.edu.wszib.media.store.dao.IUserDAO;
import pl.edu.wszib.media.store.exceptions.LoginAlreadyExistException;
import pl.edu.wszib.media.store.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements IUserDAO {
    private final List<User> users = new ArrayList<>();
    private final IdSequence idSequence;

    public UserRepository(IdSequence idSequence) {
        this.idSequence = idSequence;
        this.users.add(new User((long) this.idSequence.getId(), "Janusz", "Kowalski",
                "janusz", DigestUtils.md5DigestAsHex("janusz123".getBytes()), User.Role.USER, 100.0));
        this.users.add(new User((long) this.idSequence.getId(), "Guest", "Guest",
                "guest", DigestUtils.md5DigestAsHex("guest".getBytes()), User.Role.USER, 100.0));
        this.users.add(new User((long) this.idSequence.getId(), "admin", "admin",
                "admin", DigestUtils.md5DigestAsHex("admin".getBytes()), User.Role.ADMIN, 100.0));
    }

    @Override
    public Optional<User> getById(final Long id) {
        return this.users.stream()
                .filter(user -> user.getId().equals(id))
                .findAny()
                .map(this::copy);
    }

    @Override
    public Optional<User> getByLogin(final String login) {
        return this.users.stream()
                .filter(user -> user.getLogin().equals(login))
                .findAny()
                .map(this::copy);
    }

    @Override
    public List<User> getAll() {
        return this.users.stream().map(this::copy).toList();
    }

    @Override
    public void save(User user) {
        user.setId((long) this.idSequence.getId());
        this.getByLogin(user.getLogin()).ifPresent(u -> {
            throw new LoginAlreadyExistException();
        });
        this.users.add(user);
    }

    @Override
    public void remove(final Long id) {
        this.users.removeIf(user -> user.getId().equals(id));
    }

    @Override
    public void update(final User user) {
        this.users.stream()
                .filter(u -> u.getId().equals(user.getId()))
                .findAny()
                .ifPresent(u -> {
                    u.setName(user.getName());
                    u.setSurname(user.getSurname());
                    u.setLogin(user.getLogin());
                    u.setPassword(user.getPassword());
                    u.setRole(user.getRole());
                    u.setBalance(user.getBalance()); // Update balance as well
                });
    }

    private User copy(User user) {
        User u = new User();
        u.setId(user.getId());
        u.setName(user.getName());
        u.setSurname(user.getSurname());
        u.setLogin(user.getLogin());
        u.setPassword(user.getPassword());
        u.setRole(user.getRole());
        u.setBalance(user.getBalance()); // Update balance as well
        return u;
    }
}
