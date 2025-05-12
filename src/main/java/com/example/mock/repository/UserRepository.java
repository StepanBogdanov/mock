package com.example.mock.repository;

import com.example.mock.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.UUID;

@Repository
public class UserRepository {

    private HashMap<String, User> users = new HashMap<>();

    public String registerUser(String login, String password) {
        if (users.containsKey(login)) {
            return String.format("Пользователь логином %s уже существует", login);
        }
        UUID id = UUID.randomUUID();
        users.put(login, new User(id, login, password));
        return id.toString();
    }

    public String deleteUser(String login) {
        if (!users.containsKey(login)) {
            return String.format("Пользователя с логином %s не существует", login);
        }
        return String.format("Пользователь с логином %s был удален", login);
    }
}
