package com.pistophone.factory.repository;

import com.pistophone.factory.model.User;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    UserRepository(JdbcClient jdbcClient,
                   PasswordEncoder passwordEncoder) {
        this.jdbcClient = jdbcClient;
        this.passwordEncoder = passwordEncoder;
    }
    private JdbcClient jdbcClient;
    private PasswordEncoder passwordEncoder;
    private Logger logger = LoggerFactory.getLogger(UserRepository.class);
    private static final String SQL_FIND_BY_ID = """
            select *
            from users
            where id = ?
            """;
    private static final String SQL_SAVE_USER = """
            insert into users
            values (?, ?)
            """;
    private static final String SQL_FIND_USERS = """
            select *
            from users
            """;
    private static final String SQL_CHANGE_PASSWORD = """
            update users
            set password = ?
            where id = ?
            """;

    public Optional<User> findById(long id) {
        return jdbcClient.sql(SQL_FIND_BY_ID).param(id).query(User.class).optional();
    }
    public void saveUser(User user) {
        logger.debug("password saving: {}", user.getPassword());
        String encodedPass = passwordEncoder.encode(user.getPassword());
        logger.debug("encoded password saving: {}", encodedPass);
        jdbcClient.sql(SQL_SAVE_USER).param(user.getId()).param(encodedPass).update();
    }
    public List<User> findUsers() {
        return jdbcClient.sql(SQL_FIND_USERS).query(User.class).list();
    }
    public void changePassword(long id, String password) {
        logger.debug("new pass: {}", password);
        String encodedPass = passwordEncoder.encode(password);
        logger.debug("new hashed pass: {}", encodedPass);
        jdbcClient.sql(SQL_CHANGE_PASSWORD).param(encodedPass).param(id).update();
    }
}
