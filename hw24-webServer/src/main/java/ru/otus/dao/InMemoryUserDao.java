package ru.otus.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import ru.otus.model.User;

@SuppressWarnings({"java:S2068", "java:S2245"})
public class InMemoryUserDao implements UserDao {

    public static final String ADMIN_LOGIN = "admin";
    public static final String ADMIN_NAME = "Admin A A";
    public static final String ADMIN_PASSWORD = "11111";
    private final Map<Long, User> users;

    public InMemoryUserDao() {
        users = new HashMap<>();
        users.put(1L, new User(1L, ADMIN_NAME, ADMIN_LOGIN, ADMIN_PASSWORD));
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return users.values().stream().filter(v -> v.getLogin().equals(login)).findFirst();
    }
}
