package com.rpg.controller;

import com.rpg.model.Character;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "Hello, RPG Server is working :)";
    }

    @GetMapping("/api/v1/test")
    public Character test(){
        return new Character("Marcin", "Mouse", "Thief");
    }
}
