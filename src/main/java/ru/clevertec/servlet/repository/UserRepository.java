package ru.clevertec.servlet.repository;

import lombok.SneakyThrows;
import ru.clevertec.servlet.connection.SingletonConnection;
import ru.clevertec.servlet.entity.User;
import ru.clevertec.servlet.entity.enums.GenderEnum;
import ru.clevertec.servlet.repository.api.Repository;
import ru.clevertec.servlet.util.SQLConstant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class UserRepository implements Repository<Integer, User> {

    private static final UserRepository INSTANCE = new UserRepository();

    private final Connection connection = SingletonConnection.INSTANCE.getConnectionDB();

    @SneakyThrows
    @Override
    public List<User> findAll() {
        try (var ps = connection.prepareStatement(SQLConstant.FIND_ALL)) {
            var rs = ps.executeQuery();
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(getUser(rs));
            }
            return users;
        }
    }

    @SneakyThrows
    @Override
    public Optional<User> findById(Integer id) {
        try (var ps = connection.prepareStatement(SQLConstant.FIND_BY_ID)) {
            ps.setObject(1, id);
            var rs = ps.executeQuery();
            User user = new User();
            while (rs.next()) {
                user = getUser(rs);
            }
            return Optional.of(user);
        }
    }

    @SneakyThrows
    @Override
    public boolean delete(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(SQLConstant.DELETE_BY_ID)) {
            ps.setObject(1, id);
            ps.executeUpdate();
            return true;
        }
    }

    @Override
    public void update(User entity) {

    }

    @SneakyThrows
    @Override
    public User save(User entity) {
        try (var preparedStatement = connection.prepareStatement(SQLConstant.SAVE_SQL, RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, entity.getName());
            preparedStatement.setObject(2, entity.getEmail());
            preparedStatement.setObject(3, entity.getPassword());
            preparedStatement.setObject(4, entity.getGender().name());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            entity.setId(generatedKeys.getObject("id", Integer.class));

            return entity;
        }
    }

    public static UserRepository getInstance() {
        return INSTANCE;
    }

    private User getUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getObject("id", Integer.class),
                rs.getObject("name", String.class),
                rs.getObject("email", String.class),
                rs.getObject("password", String.class),
                GenderEnum.valueOf(rs.getObject("gender", String.class))
        );
    }
}
