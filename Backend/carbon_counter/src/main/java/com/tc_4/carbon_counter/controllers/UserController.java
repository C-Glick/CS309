package com.tc_4.carbon_counter.controllers;

import com.tc_4.carbon_counter.databases.UserDatabase;
import com.tc_4.carbon_counter.models.User;
import com.tc_4.carbon_counter.exceptions.UserNotFoundException;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    
    private final UserDatabase database;

    UserController(UserDatabase database){
        this.database = database;
    }

    @GetMapping("/getUserInfo/{id}")
    public User getUserInfo(@PathVariable Long id){
        return database.findById(id).
        orElseThrow(() -> new UserNotFoundException(id));
    }
}
