package com.java.http.dao;

import com.java.http.entity.Gender;
import com.java.http.entity.Role;
import com.java.http.entity.User;
import com.java.http.util.ConnectionManager;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class UserDao implements Dao<Integer, User> {

    private static final UserDao INSTANCE = new UserDao();

    private static final String SAVE_SQL = """
            INSERT INTO users(name, birthday, email, password, role, gender, image)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

    private static final String FIND_BY_EMAIL_AND_PASSWORD = """
            SELECT *
            FROM users
            WHERE email = ? AND password = ?
            """;

    public static UserDao getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (Connection connection = ConnectionManager.get();
            var preparedStatement = connection.prepareStatement(FIND_BY_EMAIL_AND_PASSWORD)) {
            preparedStatement.setObject(1, email);
            preparedStatement.setObject(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            User user = null;
            if (rs.next()) {
                user = buildUserEntity(rs);
            }

            return Optional.ofNullable(user);
        }
    }

    private static User buildUserEntity(ResultSet rs) throws SQLException {
        return User.builder()
                .id(rs.getObject("id", Integer.class))
                .name(rs.getObject("name", String.class))
                .birthday(rs.getObject("birthday", Date.class).toLocalDate())
                .image(rs.getObject("image", String.class))
                .email(rs.getObject("email", String.class))
                .password(rs.getObject("password", String.class))
                /* для полей, которые в БД NOT NULL */
                .role(Role.valueOf(rs.getObject("role", String.class)))
                /* для полей, которые в БД могут быть NULL */
                .gender(Gender.find(rs.getObject("gender", String.class))
                        .orElse(null))
                .build();
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public void update(User entity) {

    }

    @Override
    @SneakyThrows
    public User save(User entity) {
        try (Connection connection = ConnectionManager.get();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(SAVE_SQL, RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, entity.getName());
            preparedStatement.setObject(2, entity.getBirthday());
            preparedStatement.setObject(3, entity.getEmail());
            preparedStatement.setObject(4, entity.getPassword());
            preparedStatement.setObject(5, entity.getRole().name());
            preparedStatement.setObject(6, entity.getGender().name());
            preparedStatement.setObject(7, entity.getImage());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            entity.setId(generatedKeys.getObject("id", Integer.class));

            return entity;
        }
    }
}
