package com.tc_4.carbon_counter.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.tc_4.carbon_counter.databases.UserDatabase;
import com.tc_4.carbon_counter.models.User;
import com.tc_4.carbon_counter.models.User.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class UserServiceTest {

    @TestConfiguration
    static class configuration{

        @Bean
        public UserService userServiceConfig(){
            return new UserService();
        } 

        @Bean
        public UserDatabase userDatabaseConfig(){
            return mock(UserDatabase.class);    //create mock database
        }

        @Bean
        public CarbonUserDetailsService userDetails(){
            return new CarbonUserDetailsService();
        }
    }

    @Autowired
    private UserService userService;

    @Autowired
    private UserDatabase userDatabase;  //mock database

    private List<User> userList;

    //setup mock database and define how it should behave when 
    //it is called
    //add a test user to the database to begin
    @PostConstruct
    public void setupMockDatabase(){
        userList =  new ArrayList<User>();

        //add test user
        User testUser = new User();
        testUser.setUsername("testUsername");
        testUser.setPassword("password");
        testUser.setRole(Role.USER);
        testUser.setEmail("testEmail");
        userList.add(testUser);

        when(userDatabase.save((User)any(User.class)))
            .thenAnswer(x -> {
              User u = x.getArgument(0);
              userList.add(u);
              return u;
        });

        when(userDatabase.findAll()).thenReturn(userList);

        when(userDatabase.findByUsername((String)any(String.class)))
        .thenAnswer(x -> {
            for(User u : userList){
                if(u.getUsername().equals(x.getArgument(0))){
                    return Optional.of(u);
                }
            }
            return Optional.empty();
        });

        when(userDatabase.findById((Long)any(Long.class)))
        .thenAnswer(x -> {
            for(User u : userList){
                if(u.getId() == x.getArgument(0)){
                    return Optional.of(u);
                }
            }
            return Optional.empty();
        });
    }

    @Test
    public void testAddUser(){
        User u = new User();
        u.setUsername("addUserTest");
        u.setPassword("password");
        userService.addUser(u);

        assertEquals(Optional.of(u), userDatabase.findByUsername("addUserTest"));
    }

    @Test
    @WithUserDetails(value="testUsername")
    public void testGetUser(){
        User u = userDatabase.findByUsername("testUsername").get();
        assertEquals(u, userService.getUser("testUsername"));
    }
}
