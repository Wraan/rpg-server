package com.rpg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

}
