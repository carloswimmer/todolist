package com.carloswimmer.todolist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HomeController {
    
    @GetMapping("/")
    public String checkHealth() {
        return "It is working!";
    }
}
