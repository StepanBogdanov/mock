package com.example.mock.service;

import com.example.mock.model.RequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class ConsumerService {

    private final SupplierService supplierService;

    @Bean
    public Consumer<RequestDto> readRequest() {
        return message -> {
            switch (message.getAction()) {
                case REGISTER -> {
                    supplierService.registerUser(message.getBody());
                }
                case REQUEST -> {
                    supplierService.handleRequest(message.getBody());
                }
                case DELETE -> {
                    supplierService.deleteUser(message.getBody());
                }
            }
        };
    }

}
