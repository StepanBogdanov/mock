package com.example.mock.service;

import com.example.mock.model.RequestDto;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsumerService {

    private final SupplierService supplierService;

    @Setter
    private static long registerTimeout = 100L;
    @Setter
    private static long requestTimeout = 100L;
    @Setter
    private static long deleteTimeout = 100L;

    @Bean
    public Consumer<RequestDto> readRequest() {
        return message -> {
            log.info("Вызов метода ConsumerService.readRequest с запросом: Action = {}, Body = {}",
                    message.getAction(), message.getBody());
            switch (message.getAction()) {
                case REGISTER -> {
                    try {
                        Thread.sleep(registerTimeout);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    supplierService.registerUser(message.getBody());
                }
                case REQUEST -> {
                    try {
                        Thread.sleep(requestTimeout);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    supplierService.handleRequest(message.getBody());
                }
                case DELETE -> {
                    try {
                        Thread.sleep(deleteTimeout);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    supplierService.deleteUser(message.getBody());
                }
            }
        };
    }

}
