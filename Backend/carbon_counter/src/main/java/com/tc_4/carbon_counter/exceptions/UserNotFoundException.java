package com.tc_4.carbon_counter.exceptions;

public class UserNotFoundException extends RuntimeException{
    
    public UserNotFoundException(Long id){
        super("Could not find user with id: " + id);
    }
    public UserNotFoundException(String username){
        super("Could not find user with username: " + username);
    }
}
