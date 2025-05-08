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
        String response = userService.registerUser(body.get("login"), body.get("password"));
        streamBridge.send("registerUser-out-0", new ResponseDto(
                Action.REGISTER,
                response
        ));
        log.debug("Запрос на регистрацию пользователя с логином {} обработан, ответ: {}",
                body.get("login"), response);
    }

    public void handleRequest(HashMap<String, String> body) {
        String response = userService.handleRequest(body.get("firstName"), body.get("lastName"), body.get("middleName"));
        streamBridge.send("handleRequest-out-0", new ResponseDto(
                Action.REQUEST,
                response
        ));
        log.debug("Запрос {} обработан, ответ: {}", body, response);
    }

    public void deleteUser(HashMap<String, String> body) {
        String response = userService.deleteUser(body.get("login"));
        streamBridge.send("deleteUser-out-0", new ResponseDto(
                Action.DELETE,
                response
        ));
        log.debug("Запрос на удаление пользователя с логином {} обработан, ответ: {}",
                body.get("login"), response);
    }
}
