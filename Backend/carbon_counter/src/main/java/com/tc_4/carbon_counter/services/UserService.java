package com.tc_4.carbon_counter.services;

import com.tc_4.carbon_counter.databases.UserDatabase;
import com.tc_4.carbon_counter.exceptions.UnauthorizedException;
import com.tc_4.carbon_counter.exceptions.UserNotFoundException;
import com.tc_4.carbon_counter.models.User;
import com.tc_4.carbon_counter.models.User.Role;
import com.tc_4.carbon_counter.security.CarbonUserPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    /** Password encoder used when making a new user or changing a password*/
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Get a user object from the database based on their userName.
     * Must have at least admin permissions or authenticated as this user.
     * 
     * @param userName user name of the user to get
     * @return User object 
     * @throws UserNotFoundException
     */
    public User getUser(String userName){
        if(checkPermission(Role.ADMIN)){
            return userDatabase.findByUsername(userName).
            orElseThrow(() -> new UserNotFoundException(userName));
        }else{
            if(SecurityContextHolder.getContext().getAuthentication().getName().equals(userName)){
                return userDatabase.findByUsername(userName).
                orElseThrow(() -> new UserNotFoundException(userName));
            }
            //else unauthorized use
            throw new UnauthorizedException("You do not have permission to access user '" + userName + "'");
        }
    }

    /**
     * Add a user to the database. All user variables are required 
     * 
     * @param user to add
     * @return the user that was added to the database
     */
    public User addUser(User user){
        //encrypt password when saving to the database
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        return userDatabase.save(user);
    }

    /**
     * Change the password of the given user. Must have at least admin permissions or 
     * be authenticated as the user to change.
     * 
     * @param userName      The user name of the user to change
     * @param newPassword   The new password to use, pass as plain text, will be encrypted before saving 
     * @return  boolean, true if the password change was successful
     * @throws UnauthorizedException if you don't have permission to change this user's password
     */
    public boolean changePassword(String userName, String newPassword){
        if(!checkPermission(Role.ADMIN) && !SecurityContextHolder.getContext().getAuthentication().getName().equals(userName) ){
            throw new UnauthorizedException("You do not have permission to change the password of user '" + userName + "'");
        }

        //must have the original password which is oldPassword to change password to password with this setup
        User user = getUser(userName);

        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
            //encrypt new password            
            user.setPassword(passwordEncoder.encode(newPassword));
            userDatabase.save(user);
            return true;
        }
        throw new UnauthorizedException("Incorrect old password");
    }
  
    /**
     * Checks if the given user name exists in the user
     * database, if it does, returns true. If not, throws
     * UserNotFoundException
     * 
     * @param userName the user name to test
     * @return true if the user does exists
     * 
     * @throws UserNotFoundException if the user does not exist
     */
    public boolean doesUserExist(String userName) throws UserNotFoundException{
        if (userDatabase.existsByUsername(userName)){
            return true;
        }else{
            throw new UserNotFoundException(userName);
        }
    }

    //TODO
    public User editUser(String userName, User newUser){
        return new User();
    }

    /**
     * Preforms a check to see if the user that created this request 
     * has the required role or higher.
     * 
     * @param requiredRole  The minimum role needed to preform this action
     * @return              True if the user has access, false if they dont
     */
    public boolean checkPermission(Role requiredRole){
        CarbonUserPrincipal auth =(CarbonUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        //check all permissions including and above required role
        for(Role r : Role.values()){
            //skip lower roles
            if(r.compareTo(requiredRole) < 0 ){
                continue;
            }

            if(auth.getAuthorities().stream().anyMatch(a ->
            a.getAuthority().equals(r.toString()))){
                
                // user has at least the minimum role
                return true;
            }
        }
        //user does not have at least the minimum role
        return false;
    }
}
