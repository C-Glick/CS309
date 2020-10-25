package com.tc_4.carbon_counter.controllers;

import java.util.List;

import com.tc_4.carbon_counter.models.Friends;
import com.tc_4.carbon_counter.models.User;
import com.tc_4.carbon_counter.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The user controller provides an API to send commands to the back end.
 * The controller provides url mappings to specific logic in the service classes.
 * The user controller deals with all mappings beginning with /user/. The server
 * may respond with a "401 unauthorized" status code if the authentication header
 * is not correct. It should be a string in the following format "username:password"
 * and it should be 64 bit encoded. This header must be attached to all requests
 * except /user/add. 
 * 
 * Some functions require elevated permissions to access, these 
 * are handled by the UnauthorizedException, the response body will contain a json
 * object with a message parameter with further details. 
 * 
 * @see UserService
 * 
 * @author Colton Glick
 * @author Andrew Pester
 */
@RestController
public class UserController {

    /**
     * The user service is called by the controller, it is what actually
     * preforms the commands. 
     */
    @Autowired
    private UserService userService;


    /**
     * Get user info by username.
     * 
     * @param username pass as a path variable
     * @return The user's info as a JSON object
     */
    @GetMapping("/user/{username}")
    public User getUserInfo(@PathVariable String username){
        return userService.getUser(username);
    }

    /**
     * Add a user to the database, required fields: 
     * username, email, password, role.
     * Does not require authentication.
     * 
     * @param user to add, pass as a JSON object in the request body.
     * @return the user that has been added, may include additional
     * details from the server.
     * 
     * @see User
     * @see User.Role
     */
    @PostMapping("/user/add")
    public User addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    /**
     * Change a user's password. Must be authenticated with at least admin
     * permissions or authenticated as the user to change
     * 
     * @param username      provide as a path variable
     * @param newPassword   new password, provide as a request parameter
     * @return boolean, if the password was set 
     */
    @RequestMapping("/user/{username}/setPassword")
    public boolean setUserPassword(@PathVariable String username, 
        @RequestParam("newPassword") String newPassword)
    {
        return userService.changePassword(username, newPassword);
    }

    /**
     * Edit a user's information. Overwrites the user with the username given
     * with the user object passed in the request body. All user variables are optional
     * only specified values will be updated. Must be authenticated with at least admin
     * permissions or authenticated as the user to change.
     * 
     * @param username  provide as a path variable. The current username of the user to change
     * @param user      provide in the request body as a json object. This contains the new values to change to.
     * @return          The updated user.
     */
    @PostMapping("/user/edit/{username}")
    public User editUser(@PathVariable String username, @RequestBody User user){
        return userService.editUser(username, user);
    }

    @RequestMapping("/user/friend_request/{user}")
    public boolean friendRequest(@PathVariable String user, @RequestParam String username){
        //DONE
        return userService.friendRequest(user, username);
    }
    
    @RequestMapping("/user/friend_requests/{username}")
    public List<Friends> allFriendRequests(@PathVariable String username){
        //TODO
        //should work but security is being a pain
        //sends correct thing here but sends empty json to postman
        return userService.allFriendRequests(username);
    }

    @RequestMapping("/user/accept/{username}")
    public boolean acceptFriend(@PathVariable String username, @RequestParam String userOne){
        //DONE
        return userService.acceptFriend(username, userOne);
    }
    @RequestMapping("/user/deny/{username}")
    public boolean denyFriend(@PathVariable String username, @RequestParam String userOne){
        //DONE
        return userService.denyFriend(username, userOne);
    }

    /**
     * Remove this user from the database. Must be authenticated with at least admin
     * permissions or authenticated as the user to remove.
     * 
     * @param username  provide as a path variable. The current username of the user to remove
     * @return          boolean, true if the user was successfully removed
     */
    @DeleteMapping("/user/remove/{username}")
    public Boolean removeUser(@PathVariable String username){
        return userService.removeUser(username);
    }
}
