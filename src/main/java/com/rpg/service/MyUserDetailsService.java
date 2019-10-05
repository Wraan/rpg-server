package com.rpg.service;

import com.rpg.model.security.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface MyUserDetailsService {

    UserDetails loadUserByUsername(String username);
    //User register(UserCreateDto userCreateDto);
    List<User> saveAll(List<User> users);
}
