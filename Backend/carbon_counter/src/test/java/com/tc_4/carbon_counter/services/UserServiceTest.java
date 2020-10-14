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
import com.tc_4.carbon_counter.models.User.Role;
import org.junit.Before;
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

    // @Before
    // public void setupMockSecurity(){
    //      mockMvc = MockMvcBuilders.webAppContextSetup(wac)
    //         .apply(springSecurity())
    //         .build();
    //     auth = mock(CarbonUserPrincipal.class);
    //     Authentication authentication = mock(Authentication.class);
    //     SecurityContext securityContext = mock(SecurityContext.class);
    //     when(securityContext.getAuthentication()).thenReturn(authentication);
    //     SecurityContextHolder.setContext(securityContext);
    //     when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(auth);
    //    // when(auth.getAuthorities()).thenAnswer(x -> {
    //     //
    //   //      List<Role> grantedAuthorities = new ArrayList<Role>();
    //   //      grantedAuthorities.add(userList.get(0).getRole());
    //   //      return grantedAuthorities;
    //   //  });
    // }

    @Test
    public void testAddUser(){
        User u = new User();
        u.setUsername("testUsername");
        u.setPassword("password");
        userService.addUser(u);

        assertEquals(Optional.of(u), userDatabase.findByUsername("testUsername"));
    }

    @Test
    @WithUserDetails(value="testUsername")
    public void testGetUser(){
        User u = new User();
        u.setUsername("testUsername");
        u.setPassword("password");
        u.setRole(Role.USER);
        userService.addUser(u);
        

        assertEquals(u, userService.getUser("testUsername"));
    }
}
