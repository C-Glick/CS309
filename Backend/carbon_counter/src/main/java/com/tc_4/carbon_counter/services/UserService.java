package com.tc_4.carbon_counter.services;

import java.util.Optional;

import com.tc_4.carbon_counter.databases.UserDatabase;
import com.tc_4.carbon_counter.exceptions.UserNotFoundException;
import com.tc_4.carbon_counter.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service to handle the logic when dealing with users.
 * Able to get users, add users, remove users etc. This class
 * is made to be called from controllers.
 * @author Colton Glick
 */
@Service
public class UserService {

    @Autowired
    private UserDatabase userDatabase;

    /**
     * Get a user object from the database based on their userName.
     * 
     * @param userName
     * @return User object 
     * @throws UserNotFoundException
     */
    public User getUser(String userName){
        return userDatabase.findByUserName(userName).
        orElseThrow(() -> new UserNotFoundException(userName));
    }

    /**
     * Add a user to the database. All user variables are required 
     * 
     * @param user to add
     * @return the user that was added to the database
     */
    public User addUser(User user){
        return userDatabase.save(user);
    }

    public boolean changePassword(String userName, String oldPassword, String newPassword){
        //must have the original password which is oldPassword to change password to password with this setup
        User user = getUser(userName);

        if(user.getPassword().equals(oldPassword)){
            user.setPassword(newPassword);
            userDatabase.save(user);
            return true;
        }
        return false;
        //also just posts the new password could set method to void to stop this or whatever
        
    }
    
}
