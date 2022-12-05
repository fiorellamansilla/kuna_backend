package com.kuna_backend.dao;

import com.kuna_backend.entities.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserDaoService {

    private static List<User> users = new ArrayList<>();

    private static int usersCount = 0;

    static {
        users.add(new User(++usersCount,
                "Isabel",
                "123",
                "isabel@gmail.com",
                "Peru",
                Boolean.FALSE,
                Boolean.TRUE,
                LocalDateTime.now()));
    };

    // GET all users Method
    public List<User> findAll() {
        return users;
    }

    // Get one User by ID Method
    public User findOne(int id) {
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        return users.stream().filter(predicate).findFirst().orElse(null);
    }

    // POST a client Method
    public User save(User user) {
        user.setId(++usersCount);
        users.add(user);
        return user;
    }

    // Delete User by Id Method
    public void deleteById(int id) {
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        users.removeIf(predicate);
    }
}
