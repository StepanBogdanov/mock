package com.example.mock.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logging")
@RequiredArgsConstructor
public class LoggingController {

    private final LoggingSystem loggingSystem;

    @PostMapping("/debug")
    public ResponseEntity<String> setDebugLevel() {
        try {
            loggingSystem.setLogLevel("ROOT", LogLevel.DEBUG);
            return ResponseEntity.ok("Log level set to DEBUG");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to set log level: " + e.getMessage());
        }
    }

    @PostMapping("/error")
    public ResponseEntity<String> setErrorLevel() {
        try {
            loggingSystem.setLogLevel("ROOT", LogLevel.ERROR);
            return ResponseEntity.ok("Log level set to ERROR");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to set log level: " + e.getMessage());
        }
    }
}
