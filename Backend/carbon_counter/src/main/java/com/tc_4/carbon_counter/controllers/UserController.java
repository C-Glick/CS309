package com.tc_4.carbon_counter.controllers;

import com.tc_4.carbon_counter.models.User;
import com.tc_4.carbon_counter.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
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
 * The user controller deals with all mappings beginning with /user/
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
     * @param userName pass as a path variable
     * @return The user's info as a JSON object
     */
    @GetMapping("/user/{userName}")
    public User getUserInfo(@PathVariable String userName){
        return userService.getUser(userName);
    }

    /**
     * Add a user to the database, required fields: 
     * userName, email, password, role.
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
     * Change a user password, must provide the correct 
     * old password and a new password as a request parameter.
     * 
     * @param userName      provide as a path variable
     * @param oldPassword   old password must match current password, provide as a request parameter
     * @param newPassword   new password, provide as a request parameter
     * @return boolean, if the password was set 
     */
    @RequestMapping("/user/{userName}/setPassword")
    public boolean setUserPassword(@PathVariable String userName, @RequestParam("oldPassword") String oldPassword, 
    @RequestParam("newPassword") String newPassword)
    {
        return userService.changePassword(userName, oldPassword, newPassword);
    }
}
