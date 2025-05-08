package com.example.mock.controller;

import com.example.mock.service.ConsumerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/timeout")
public class TimeoutController {

    @PostMapping("/register")
    public void setRegisterTimeout(@RequestParam long timeout) {
        ConsumerService.setRegisterTimeout(timeout);
    }

    @PostMapping("/request")
    public void setRequestTimeout(@RequestParam long timeout) {
        ConsumerService.setRequestTimeout(timeout);
    }

    @PostMapping("/delete")
    public void setDeleteTimeout(@RequestParam long timeout) {
        ConsumerService.setDeleteTimeout(timeout);
    }
}
