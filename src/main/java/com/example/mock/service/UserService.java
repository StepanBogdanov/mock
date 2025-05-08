package com.example.mock.service;

import com.example.mock.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public String registerUser(String login, String password) {
        return userRepository.registerUser(login, password);
    }

    public String handleRequest(String firstName, String lastName, String middleName) {
        return lastName;
    }

    public String deleteUser(String login) {
        return userRepository.deleteUser(login);
    }
}
