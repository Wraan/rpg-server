package com.rpg.service;

import com.rpg.dto.UserRegistrationFormDto;

public interface UserService {
    void register(UserRegistrationFormDto registeredUser) throws Exception;
}
