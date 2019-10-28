package com.rpg.service.security;

import com.rpg.dto.UserRegistrationFormDto;
import com.rpg.model.security.User;

public interface UserService {
    void register(UserRegistrationFormDto registeredUser) throws Exception;
    User findByUsername(String username);
    User findWithToken(String token) throws Exception;
}
