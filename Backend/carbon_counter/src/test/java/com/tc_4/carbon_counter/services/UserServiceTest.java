package com.tc_4.carbon_counter.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.tc_4.carbon_counter.databases.UserDatabase;
import com.tc_4.carbon_counter.models.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
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
    }

    @Autowired
    private UserService userService;

    @Autowired
    private UserDatabase userDatabase;  //mock database

    private List<User> userList;

    //setup mock database and define how it should behave when 
    //it is called
    @Before
    public void setupMockDatabase(){
        userList =  new ArrayList<User>();
        when(userDatabase.save((User)any(User.class)))
        .thenAnswer(x -> {
              User u = x.getArgument(0);
              userList.add(u);
              return null;
        });

        when(userDatabase.findAll()).thenReturn(userList);

        when(userDatabase.findByUsername((String)any(String.class)))
        .thenAnswer(x -> {
            return Optional.of(userList.get(0));
        });

        when(userDatabase.findById((Long)any(Long.class)))
        .thenAnswer(x -> {
            return userList.get(0);
        });
    }

    @Test
    public void testAddUser(){
        User u = new User();
        u.setUsername("testUsername");
        u.setPassword("password");
        userService.addUser(u);

        assertEquals(Optional.of(u), userDatabase.findByUsername("testUsername"));
    }
}
