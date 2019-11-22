package com.rpg.service.security;

import com.rpg.dto.UserRegistrationFormDto;
import com.rpg.dto.application.ChangePasswordDto;
import com.rpg.model.security.User;

public interface UserService {
    void register(UserRegistrationFormDto registeredUser) throws Exception;
    User findByUsername(String username);
    boolean existByUsername(String username);

    void changePassword(User user, ChangePasswordDto changePasswordDto) throws Exception;
}
