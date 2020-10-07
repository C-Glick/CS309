package com.tc_4.carbon_counter.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User{
    /**
     * Enumeration for the role of each user. Determines 
     * the permissions that this user has.
     */
    public enum Role{
        USER,
        CREATOR,
        ADMIN,
        DEV
    } 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
  
    @Column(name="username")
    String username;

    @Column(name="email")
    String email;

    @Column(name="password")
    String password;

    @Column(name="role")
    Role role;

    public Long getId(){
        return id;
    }

    public String getUsername(){
        return username;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }
    public Role getRole(){
        return role;
    }
    public void setUsername(String name){
        username = name;
    }
    public void setEmail(String newEmail){
        email = newEmail;
    }
    public void setPassword(String newPass){
        password = newPass;
    }
    public void setRole(Role newRole){
        role = newRole;
    }
    public boolean checkPermission(Role permission){
        //need to test
        return role.compareTo(permission)>=0;
    }

}
