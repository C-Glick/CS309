package com.tc_4.carbon_counter.controllers;

import com.tc_4.carbon_counter.databases.UserDatabase;
import com.tc_4.carbon_counter.models.User;
import com.tc_4.carbon_counter.exceptions.UserNotFoundException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    
    private final UserDatabase database;

    UserController(UserDatabase database){
        this.database = database;
    }

    @GetMapping("/user/{userName}")
    public User getUserInfo(@PathVariable String userName){
        return database.findByUserName(userName).
        orElseThrow(() -> new UserNotFoundException(userName));
    }

    @PostMapping("/addUser")
    public User addUser(@RequestBody User user){
        database.save(user);
        return user;
    }
    @RequestMapping("/user/{userName}/setPassword")
    public String setUserPassword(@PathVariable String userName, @RequestParam("newPassword") String newPassword,
    @RequestParam("oldPassword") String oldPassword)
    {
        //must have the original password which is oldPassword to change password to password with this setup
        if(database.findByUserName(userName).get().getPassword().equals(oldPassword)){
            database.findByUserName(userName).get().setPassword(newPassword);
            database.save(database.findByUserName(userName).get());
            return "The password is now " + newPassword;
        }
        return "The password you entered is incorrect";
        //also just posts the new password could set method to void to stop this or whatever
        
            
    }
}
