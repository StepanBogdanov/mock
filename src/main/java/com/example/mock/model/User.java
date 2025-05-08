package com.example.mock.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class User {

    private UUID id;
    private String login;
    private String password;
}
