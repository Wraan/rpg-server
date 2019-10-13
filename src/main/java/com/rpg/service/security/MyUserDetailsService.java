package com.rpg.service.security;

import com.rpg.model.security.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface MyUserDetailsService {

    UserDetails loadUserByUsername(String username);
    List<User> saveAll(List<User> users);
}
