package com.tc_4.carbon_counter;

import static org.junit.Assert.assertEquals;

import com.tc_4.carbon_counter.models.User;
import com.tc_4.carbon_counter.models.User.Role;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserTest {
    
    User user;

    @Before
    public void beforeTest(){
        user = new User();
    }

    @Test
    public void testEmptyConstructor(){      
        assertEquals(null, user.getId());
        assertEquals(null, user.getEmail());
        assertEquals(null, user.getPassword());
        assertEquals(null, user.getUsername());
        assertEquals(null, user.getRole());
    }

    @Test
    public void testUsername(){
        user.setUsername("testUsername");
        assertEquals("testUsername", user.getUsername());
    }

    @Test
    public void testEmail(){
        user.setEmail("testEmail@gmail.com");
        assertEquals("testEmail@gmail.com", user.getEmail());
    }

    @Test
    public void testPassword(){
        user.setPassword("testPassword");
        assertEquals("testPassword", user.getPassword());
    }

    @Test
    public void testRole(){
        user.setRole(Role.ADMIN);
        assertEquals(Role.ADMIN, user.getRole());
    }

    @Test
    public void testCheckPermission(){
        user.setRole(Role.USER);
        assertEquals(true, user.checkPermission(Role.USER));
        assertEquals(false, user.checkPermission(Role.CREATOR));
        assertEquals(false, user.checkPermission(Role.ADMIN));
        assertEquals(false, user.checkPermission(Role.DEV));

        user.setRole(Role.CREATOR);
        assertEquals(true, user.checkPermission(Role.USER));
        assertEquals(true, user.checkPermission(Role.CREATOR));
        assertEquals(false, user.checkPermission(Role.ADMIN));
        assertEquals(false, user.checkPermission(Role.DEV));

        user.setRole(Role.ADMIN);
        assertEquals(true, user.checkPermission(Role.USER));
        assertEquals(true, user.checkPermission(Role.CREATOR));
        assertEquals(true, user.checkPermission(Role.ADMIN));
        assertEquals(false, user.checkPermission(Role.DEV));

        user.setRole(Role.DEV);
        assertEquals(true, user.checkPermission(Role.USER));
        assertEquals(true, user.checkPermission(Role.CREATOR));
        assertEquals(true, user.checkPermission(Role.ADMIN));
        assertEquals(true, user.checkPermission(Role.DEV));
    }
    
    @Test
    public void testCopyFrom(){
        user.setEmail("testEmail@gmail.com");
        user.setUsername("testUsername");
        user.setPassword("testPassword");
        user.setRole(Role.USER);

        User changes = new User();
        changes.setEmail("newEmail@gmail.com");
        changes.setPassword("newPassword");
        user.copyFrom(changes);

        assertEquals("newEmail@gmail.com", user.getEmail());
        assertEquals("testUsername", user.getUsername());
        assertEquals("testPassword", user.getPassword());   //does not copy password
        assertEquals(Role.USER, user.getRole());
    }
}