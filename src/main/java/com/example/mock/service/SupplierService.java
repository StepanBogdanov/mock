package com.example.mock.service;

import com.example.mock.model.Action;
import com.example.mock.model.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final UserService userService;
    private final StreamBridge streamBridge;

    public void registerUser(HashMap<String, String> body) {
        String response = userService.registerUser(body.get("login"), body.get("password"));
        streamBridge.send("registerUser-out-0", new ResponseDto(
                Action.REGISTER,
                response
        ));
    }

    public void handleRequest(HashMap<String, String> body) {
        String response = userService.handleRequest(body.get("firstName"), body.get("lastName"), body.get("middleName"));
        streamBridge.send("handleRequest-out-0", new ResponseDto(
                Action.REQUEST,
                response
        ));
    }

    public void deleteUser(HashMap<String, String> body) {
        String response = userService.deleteUser(body.get("login"));
        streamBridge.send("deleteUser-out-0", new ResponseDto(
                Action.DELETE,
                response
        ));
    }
}
