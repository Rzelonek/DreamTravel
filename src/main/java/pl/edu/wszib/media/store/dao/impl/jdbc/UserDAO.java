package pl.edu.wszib.media.store.dao.impl.jdbc;

import org.springframework.stereotype.Repository;

import pl.edu.wszib.media.store.dao.IUserDAO;
import pl.edu.wszib.media.store.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO implements IUserDAO {

    private final Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<User> getById(Long id) {
        String sql = "SELECT * FROM tuser WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setRole(User.Role.valueOf(rs.getString("role")));
                user.setPassword(rs.getString("password"));
                user.setLogin(rs.getString("login"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                return Optional.of(user);
            }
        } catch (SQLException e) {
            System.out.println("User entity error !!");
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getByLogin(String login) {
        String sql = "SELECT * FROM tuser WHERE login = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, login);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setRole(User.Role.valueOf(rs.getString("role")));
                user.setPassword(rs.getString("password"));
                user.setLogin(rs.getString("login"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                return Optional.of(user);
            }
        } catch (SQLException e) {
            System.out.println("User entity error !!");
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM tuser";
        List<User> result = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setRole(User.Role.valueOf(rs.getString("role")));
                user.setPassword(rs.getString("password"));
                user.setLogin(rs.getString("login"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                result.add(user);
            }
        } catch (SQLException e) {
            System.out.println("User entity error !!");
        }
        return result;
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO tuser (name, surname, login, password, role) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getRole().name());

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            user.setId(rs.getLong(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(Long id) {
        String sql = "DELETE FROM tuser WHERE id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE tuser SET name = ?, surname = ?, login = ?, password = ?, role = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getRole().name());
            preparedStatement.setLong(6, user.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
