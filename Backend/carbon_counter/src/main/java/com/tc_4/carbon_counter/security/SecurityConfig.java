package com.tc_4.carbon_counter.security;

import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import com.tc_4.carbon_counter.databases.UserDatabase;
import com.tc_4.carbon_counter.models.User;
import com.tc_4.carbon_counter.services.CarbonUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.context.WebApplicationContext;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDatabase userDatabase;
    
    @Autowired
    private WebApplicationContext applicationContext;
    @Autowired
    private CarbonUserDetailsService userDetailsService;

    @Autowired 
    private DataSource datasource;

    @PostConstruct
    public void completeSetup() {
        userDetailsService = applicationContext.getBean(CarbonUserDetailsService.class);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
        .csrf().disable()
        .authorizeRequests()
            .antMatchers("/", "/user/add").permitAll()
            .anyRequest().authenticated()            
        .and().httpBasic();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(encoder())
            .and()
            .authenticationProvider(authenticationProvider())
            .jdbcAuthentication()
            .dataSource(datasource);


        /*List<User> users = userDatabase.findAll();

        //add all users to in memory authentication
        for(User user : users){

            auth.jdbcAuthentication()
            .withUser(user.getUsername())
            .password("{noop}" + user.getPassword())
            .roles(user.getRole().toString());
        } */
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }


    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder(11);
    }
}
