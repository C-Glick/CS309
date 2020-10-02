package com.tc_4.carbon_counter;

import java.util.List;

import com.tc_4.carbon_counter.databases.UserDatabase;
import com.tc_4.carbon_counter.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDatabase userDatabase;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
        .csrf().disable()
        .authorizeRequests().anyRequest().authenticated()
        .and().httpBasic();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{

        List<User> users = userDatabase.findAll();

        //add all users to in memory authentication
        for(User user : users){
            auth.inMemoryAuthentication()
            .withUser(user.getUserName())
            .password("{noop}" + user.getPassword())
            .roles(user.getRole().toString());
        } 
    }
}
