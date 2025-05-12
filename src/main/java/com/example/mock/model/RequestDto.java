package com.example.mock.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {

    private Action action;
    private HashMap<String, String> body;

}
