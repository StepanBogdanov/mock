package com.example.mock.service;

import com.example.mock.model.Action;
import com.example.mock.model.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class SupplierService {

    private final UserService userService;
    private final StreamBridge streamBridge;

    public void registerUser(HashMap<String, String> body) {
        String login = body.get("login");
        String password = body.get("password");
        String response;
        if (login.isBlank()) {
            response = "В запрос на регистрацию передан пустой логин";
            log.info(response);
        } else if (login.length() < 5) {
            response = "Логин не может быть меньше пяти символов: " + login;
            log.info(response);
        } else if (password.isBlank()) {
            response = "В запросе на регистрацию передан пустой пароль";
            log.info(response);
        } else if (!password.matches("^[a-zA-Z0-9]{8,}$")) {
            response = "Пароль должен содержать только латинские буквы и цифры и не должен быть короче 8 символов: " + password;
            log.info(response);
        } else {
            response = userService.registerUser(login, password);
        }
        streamBridge.send("registerUser-out-0", new ResponseDto(
                Action.REGISTER,
                response
        ));
        log.info("Запрос на регистрацию пользователя с логином {} обработан, ответ: {}",
                body.get("login"), response);
    }

    public void handleRequest(HashMap<String, String> body) {
        String firstName = body.get("firstName");
        String lastName = body.get("lastName");
        String middleName = body.get("middleName");
        String response;
        if (firstName.isBlank() || lastName.isBlank() || middleName.isBlank()) {
            response = String.format("В запросе переданы пустое имя: %s, фамилия: %s или отчество: %s",
                    firstName, lastName, middleName);
            log.info(response);
        } else {
            response = userService.handleRequest(firstName, lastName, middleName);
        }
        streamBridge.send("handleRequest-out-0", new ResponseDto(
                Action.REQUEST,
                response
        ));
        log.info("Запрос {} обработан, ответ: {}", body, response);
    }

    public void deleteUser(HashMap<String, String> body) {
        String login = body.get("login");
        String response;
        if (login.isBlank()) {
            response = "В запрос на удаление передан пустой логин";
            log.info(response);
        } else if (login.length() < 5) {
            response = "Логин не может быть меньше пяти символов: " + login;
            log.info(response);
        } else {
            response = userService.deleteUser(login);
        }
        streamBridge.send("deleteUser-out-0", new ResponseDto(
                Action.DELETE,
                response
        ));
        log.info("Запрос на удаление пользователя с логином {} обработан, ответ: {}",
                body.get("login"), response);
    }
}
