package com.rpg.service.security;

import com.rpg.dto.UserRegistrationFormDto;
import com.rpg.exception.CredentialsFormatException;
import com.rpg.exception.EmailAlreadyExistsException;
import com.rpg.exception.UserAlreadyExistsException;
import com.rpg.model.security.User;
import com.rpg.repository.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.regex.Pattern;

@Service("userService")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final static String EMAIL_REGEX = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
    private final static String USERNAME_REGEX = "^[a-z0-9]{4,24}$";
    private final static String PASSWORD_REGEX = "^[a-zA-Z0-9]{6,24}$";

    public void register(UserRegistrationFormDto registeredUser) throws Exception {
        if (!validateCredentials(registeredUser))
            throw new CredentialsFormatException("Invalid format exception!");
        if(userRepository.existsByUsername(registeredUser.getUsername()))
            throw new UserAlreadyExistsException("User already exists!");
        if(userRepository.existsByEmail(registeredUser.getEmail()))
            throw new EmailAlreadyExistsException("Email already exists!");


        User user = new User(registeredUser.getUsername(), passwordEncoder.encode(registeredUser.getPassword()), registeredUser.getEmail(),
                true, true, true, true,
                Collections.singletonList(roleService.findByName("USER")));

        userRepository.save(user);
    }

    private boolean validateCredentials(UserRegistrationFormDto registeredUser){
        Pattern usernameReg = Pattern.compile(USERNAME_REGEX);
        Pattern emailReg = Pattern.compile(EMAIL_REGEX);
        Pattern passwordReg = Pattern.compile(PASSWORD_REGEX);

        if(!usernameReg.matcher(registeredUser.getUsername()).matches())
            return false;
        if (!emailReg.matcher(registeredUser.getEmail()).matches())
            return false;
        if(!passwordReg.matcher(registeredUser.getPassword()).matches())
            return false;

        return true;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
