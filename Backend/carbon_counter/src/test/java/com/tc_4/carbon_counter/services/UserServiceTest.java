package com.tc_4.carbon_counter.services;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.tc_4.carbon_counter.databases.FriendsDatabase;
import com.tc_4.carbon_counter.databases.UserDatabase;
import com.tc_4.carbon_counter.exceptions.RequestExistsException;
import com.tc_4.carbon_counter.exceptions.RequestNotFoundException;
import com.tc_4.carbon_counter.exceptions.UserNotFoundException;
import com.tc_4.carbon_counter.models.Friends;
import com.tc_4.carbon_counter.models.User;
import com.tc_4.carbon_counter.models.Friends.Status;
import com.tc_4.carbon_counter.models.User.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Colton Glick
 */
@RunWith(SpringRunner.class)
@ContextConfiguration
public class UserServiceTest {

    @TestConfiguration
    static class configuration{

        //system under test
        @Bean
        public UserService userServiceConfig(){
            return new UserService();
        } 

        //mock database backed by a list
        @Bean
        public UserDatabase userDatabaseConfig(){
            return mock(UserDatabase.class);
        }

        @Bean
        public FriendsDatabase friendsDatabaseConfig(){
            return mock(FriendsDatabase.class);
        }


        //needed for authentication to work correctly
        @Bean
        public CarbonUserDetailsService userDetails(){
            return new CarbonUserDetailsService();
        }
    }

    @Autowired
    private UserService userService;

    @Autowired
    private UserDatabase userDatabase;  //mock database

    @Autowired
    private FriendsDatabase friendsDatabase;

    private List<Friends> friendsList;

    private List<User> userList;    //list to hold data for mock database

    /**
     * setup mock database and define how it should behave when 
     * it is called
     * add a test user to the database to begin
     * 
     * using postConstruct instead of before so that the mock database
     * is setup before spring boot attempts to find the test user
     * for authentication
     */
    @PostConstruct
    public void setupMockObjects(){
        userList =  new ArrayList<User>();
        friendsList = new ArrayList<Friends>();

        //add test user
        User testUser = new User();
        testUser.setUsername("testUsername");
        testUser.setPassword("password");
        testUser.setRole(Role.USER);
        testUser.setEmail("testEmail");
        userList.add(testUser);
        //this cause tests to fail
        /*
        User testUser2 = new User();
        testUser.setUsername("testUser2");
        testUser.setPassword("password");
        testUser.setRole(Role.USER);
        testUser.setEmail("testEmail");
        userList.add(testUser2);
        */
        //add test friends
        Friends testFriend1 = new Friends();
        testFriend1.setUserOne("testUsername");
        testFriend1.setUserTwo("testUser2");
        testFriend1.setStatus(Status.REQUESTED);
        friendsList.add(testFriend1);
        


        //save
        when(userDatabase.save((User)any(User.class)))
            .thenAnswer(x -> {
              User u = x.getArgument(0);
              userList.add(u);
              return u;
        });
        
        when(friendsDatabase.save((Friends)any(Friends.class)))
        .thenAnswer(x -> {
            Friends f = x.getArgument(0);
            friendsList.add(f);
            return f;
        });
        

        //delete
        doAnswer((x) -> {
			userList.remove(x.getArgument(0));
			return null;
        }).when(userDatabase).delete(any());
        
        
        doAnswer(x -> {
            friendsList.remove(x.getArgument(0));
            return null;
        }).when(friendsDatabase).delete(any());
        
        //findAll
        when(userDatabase.findAll()).thenReturn(userList);

        //findByUsername
        when(userDatabase.findByUsername((String)any(String.class)))
        .thenAnswer(x -> {
            for(User u : userList){
                if(u.getUsername().equals(x.getArgument(0))){
                    return Optional.of(u);
                }
            }
            return Optional.empty();
        });

        //findById
        when(userDatabase.findById((Long)any(Long.class)))
        .thenAnswer(x -> {
            for(User u : userList){
                if(u.getId() == x.getArgument(0)){
                    return Optional.of(u);
                }
            }
            return Optional.empty();
        });

        //existsByUsername
        when(userDatabase.existsByUsername((String)any(String.class)))
        .thenAnswer(x -> {
            return userDatabase.findByUsername(x.getArgument(0)).isPresent();
        });
        
        //findByUserOneAndUserTwo
        when(friendsDatabase.findByUserOneAndUserTwo((String)any(String.class), (String)any(String.class)))
        .thenAnswer(x -> {
            for(Friends f: friendsList){
                if(f.getUserOne().equals(x.getArgument(0)) && f.getUserTwo().equals(x.getArgument(1))){
                    return Optional.of(f);
                }

            }
            return Optional.empty();
        });

        //findByUserTwoAndStatus
        when(friendsDatabase.findByUserTwoAndStatus((String)any(String.class),(Status)any(Status.class)))
        .thenAnswer(x -> {
            List<Friends> requests = new ArrayList<>();

            for(Friends f: friendsList){
                if(f.getUserTwo().equals(x.getArgument(0)) && f.getStatus().equals(x.getArgument(1))){
                    requests.add(f);
                }
            }
            return requests;
        });
        

    }

    // withUserDetails annotation tells spring to authenticate as this user when 
    // running methods that require authentication checking. The user must exist in 
    // the mock database before each test is called
    @Test
    @WithUserDetails("testUsername")
    public void testDoesUserExist(){
        assertThrows(UserNotFoundException.class, () -> {userService.doesUserExist("nonexistent_user");});
        assertEquals(true, userService.doesUserExist("testUsername"));
    }
    
    @Test
    public void testFriendRequest(){
        //DONE
        //have to create second user in test as to not break other tests
        User testUser2 = new User();
        testUser2.setUsername("testUser2");
        testUser2.setPassword("password");
        testUser2.setRole(Role.USER);
        testUser2.setEmail("testEmail");
        userList.add(testUser2);
        assertThrows(RequestExistsException.class, () -> {userService.friendRequest("testUsername", "testUser2");});
        assertThrows(UserNotFoundException.class, () -> {userService.friendRequest("nonexistent_user", "testUser2");});
        //removes existing friendship
        Friends temp  = new Friends();
        temp.setUserOne("testUsername");
        temp.setUserTwo("testUser2");
        temp.setStatus(Status.REQUESTED);
        friendsDatabase.delete(temp);
        friendsList.clear();
        assertEquals(true, userService.friendRequest("testUsername", "testUser2"));

    }

    @Test
    public void testAllFriendRequest(){
        //DONE
        List<Friends> results = userService.allFriendRequests("testUser2");
        assertEquals(results.get(0), friendsList.get(0));
    }

    @Test
    public void testAcceptFriend(){
        assertThrows(UserNotFoundException.class, () ->{userService.acceptFriend("username", "userOne");});
        User testUser2 = new User();
        testUser2.setUsername("testUser2");
        testUser2.setPassword("password");
        testUser2.setRole(Role.USER);
        testUser2.setEmail("testEmail");
        userList.add(testUser2);
        friendsList.get(0).setStatus(Status.APPROVED);
        assertEquals(true, userService.acceptFriend("testUser2", "testUsername"));
        friendsDatabase.delete(friendsList.get(0));
        friendsList.clear();
        assertThrows(RequestNotFoundException.class, () ->{userService.acceptFriend("testUser2", "testUsername");});


    }

    @Test
    public void testDenyFriend(){
        assertThrows(UserNotFoundException.class, () ->{userService.denyFriend("username", "userOne");});
        User testUser2 = new User();
        testUser2.setUsername("testUser2");
        testUser2.setPassword("password");
        testUser2.setRole(Role.USER);
        testUser2.setEmail("testEmail");
        userList.add(testUser2);
        friendsList.get(0).setStatus(Status.DENIED);
        assertEquals(true, userService.denyFriend("testUser2", "testUsername"));
        friendsDatabase.delete(friendsList.get(0));
        friendsList.clear();
        assertThrows(RequestNotFoundException.class, () ->{userService.acceptFriend("testUser2", "testUsername");});
    }
    

    @Test
    @WithUserDetails("testUsername")
    public void testAddUser(){
        User u = new User();
        u.setUsername("addUserTest");
        u.setPassword("password");
        userService.addUser(u);

        assertEquals(Optional.of(u), userDatabase.findByUsername("addUserTest"));
    }

    @Test
    @WithUserDetails("testUsername")
    public void testGetUser(){
        User u = userDatabase.findByUsername("testUsername").get();
        assertEquals(u, userService.getUser("testUsername"));
    }

    @Test
    @WithUserDetails("testUsername")
    public void testChangePassword(){
        String before = userDatabase.findByUsername("testUsername").get().getPassword();

        userService.changePassword("testUsername", "newTestPassword");

        String after = userDatabase.findByUsername("testUsername").get().getPassword();

        //check if password has changed
        assertEquals(false, before.equals(after));
    }

    @Test
    @WithUserDetails("testUsername")
    public void testEditUser(){
        User u = userDatabase.findByUsername("testUsername").get();

        User changes = new User();
        changes.setEmail("changedEmail");
        changes.setUsername("changedUsername");

        userService.editUser("testUsername", changes);

        assertEquals("changedEmail", u.getEmail());
        assertEquals("changedUsername", u.getUsername());
    }

    @Test
    @WithUserDetails("testUsername")
    public void testRemoveUser(){
        userService.removeUser("testUsername");
        assertEquals(false, userDatabase.findByUsername("testUsername").isPresent());
    }

    @Test
    @WithUserDetails("testUsername")
    public void testCheckPermission(){
        assertEquals(true, userService.checkPermission(Role.USER));
        assertEquals(false, userService.checkPermission(Role.CREATOR));
        assertEquals(false, userService.checkPermission(Role.ADMIN));
        assertEquals(false, userService.checkPermission(Role.DEV));
    }

}
