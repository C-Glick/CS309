package com.tc_4.carbon_counter.exceptions;

public class UserNotFoundException extends RuntimeException{
    
    public UserNotFoundException(String userName){
        super("Could not find user with userName: " + userName);
        
    }
}
