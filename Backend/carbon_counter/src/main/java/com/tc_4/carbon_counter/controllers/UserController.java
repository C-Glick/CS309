package com.tc_4.carbon_counter.controllers;

import com.tc_4.carbon_counter.databases.UserDatabase;
import com.tc_4.carbon_counter.models.User;
import com.tc_4.carbon_counter.exceptions.UserNotFoundException;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    
    private final UserDatabase database;

    UserController(UserDatabase database){
        this.database = database;
    }

    @GetMapping("/user/{username}")
    public User getUserInfo(@PathVariable String username){
        //need to change to find username
        if(database.findAll().indexOf(username) == -1){
            throw new UserNotFoundException(username);
        }else{
            return database.findAll().get(database.findAll().indexOf(username));
        }
        //return 
        //orElseThrow(() -> new UserNotFoundException(username));
        
        //
    }

    @PostMapping("/addUser")
    public User addUser(@RequestBody User user){
        database.save(user);
        return user;
    }
}
