package com.rpg.controller;

import com.rpg.dto.UserRegistrationFormDto;
import com.rpg.dto.application.ChangePasswordDto;
import com.rpg.model.security.User;
import com.rpg.service.security.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;

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
    @PatchMapping("/action/changePassword")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity changePassword(@RequestBody ChangePasswordDto changePasswordDto,
                                         Principal principal){
        User user = userService.findByUsername(principal.getName());
        try {
            userService.changePassword(user, changePasswordDto);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
