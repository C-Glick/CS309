package com.tc_4.carbon_counter;

import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import com.tc_4.carbon_counter.databases.UserDatabase;
import com.tc_4.carbon_counter.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDatabase userDatabase;

    @Autowired
    public DataSource dataSource;
    
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

            auth.jdbcAuthentication()
            .withUser(user.getUserName())
            .password("{noop}" + user.getPassword())
            .roles(user.getRole().toString());
        } 
    }

    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager() {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
        jdbcUserDetailsManager.setDataSource(dataSource);
        //users.put("user","pass,ROLE_USER,enabled"); //add whatever other user you need
        return jdbcUserDetailsManager;
    }
}
