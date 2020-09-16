package com.tc_4.carbon_counter.databases;

import java.util.Optional;

import com.tc_4.carbon_counter.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDatabase extends JpaRepository<User, Long> {
    
    Optional<User> findByUserName(String userName);
}
