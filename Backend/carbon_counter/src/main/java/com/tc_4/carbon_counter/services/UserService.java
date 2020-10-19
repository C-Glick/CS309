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
     * Get a user object from the database based on their username.
     * Must have at least admin permissions or authenticated as this user.
     * 
     * @param username  user name of the user to get
     * @return          User object 
     * @throws UserNotFoundException
     */
    public User getUser(String username){
        if(checkPermission(Role.ADMIN)){
            return userDatabase.findByUsername(username).
            orElseThrow(() -> new UserNotFoundException(username));
        }else{
            if(SecurityContextHolder.getContext().getAuthentication().getName().equals(username)){
                return userDatabase.findByUsername(username).
                orElseThrow(() -> new UserNotFoundException(username));
            }
            //else unauthorized use
            throw new UnauthorizedException("You do not have permission to access user '" + username + "'");
        }
    }

    /**
     * Add a user to the database. All user variables are required 
     * 
     * @param user  to add
     * @return      the user that was added to the database
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
     * @param username      The user name of the user to change
     * @param newPassword   The new password to use, pass as plain text, will be encrypted before saving 
     * @return              boolean, true if the password change was successful
     * @throws UnauthorizedException if you don't have permission to change this user's password
     */
    public boolean changePassword(String username, String newPassword){
        if(!checkPermission(Role.ADMIN) && !SecurityContextHolder.getContext().getAuthentication().getName().equals(username) ){
            throw new UnauthorizedException("You do not have permission to change the password of user '" + username + "'");
        }

        //must have the original password which is oldPassword to change password to password with this setup
        User user = getUser(username);

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
     * @param username  the user name to test
     * @return          true if the user does exists
     * 
     * @throws UserNotFoundException if the user does not exist
     */
    public boolean doesUserExist(String username) throws UserNotFoundException{
        if (userDatabase.existsByUsername(username)){
            return true;
        }else{
            throw new UserNotFoundException(username);
        }
    }

    /**
     * Edit the user's information with the passed in information.
     * Must have at least admin permissions or be authenticated as the user to change
     * 
     * @param username      The current username of the user to edit
     * @param userChanges   A user object that *holds only the changes to be made* null variables will be unchanged
     * @return              the new user after edits have been made
     */
    public User editUser(String username, User userChanges){
        User user = getUser(username);

        //if changing password 
        if(userChanges.getPassword()!=null){
            changePassword(username, userChanges.getPassword());
        }
        //copy all other values over
        user.copyFrom(userChanges);

        userDatabase.save(user);
        return user;
    }

    /**
     * Remove the specified user from the user database.
     * 
     * @param username  The username of the user to remove
     * @return          boolean, true if the user was successfully removed
     */
    public boolean removeUser(String username){
        userDatabase.delete(getUser(username));
        //TODO: delete all user stats too
        return true;
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
