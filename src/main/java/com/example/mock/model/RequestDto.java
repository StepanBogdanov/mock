package com.example.mock.model;

import lombok.Data;

import java.util.HashMap;

@Data
public class RequestDto {

    private Action action;
    private HashMap<String, String> body;

}
