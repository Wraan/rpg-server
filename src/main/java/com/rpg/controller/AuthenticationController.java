package com.rpg.controller;

import com.rpg.dto.UserRegistrationFormDto;
import com.rpg.service.security.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AuthenticationController {

    @Autowired
    private UserService userService;

    private Logger LOGGER = LogManager.getLogger(getClass());

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserRegistrationFormDto registeredUser) {
        try {
            userService.register(registeredUser);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
