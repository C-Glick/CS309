package com.tc_4.carbon_counter.databases;
import java.util.Optional;

import com.tc_4.carbon_counter.models.Tip;
import com.tc_4.carbon_counter.models.Tip.Catagory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TipsDatabase extends JpaRepository<Tip, Long> {
    
    Optional<Tip> findByTitle(String title);
    Optional<Tip> findByWorkingTitle(String workingTitle);
    Optional<Tip> findByCatagory(Catagory catagory);
    Optional<Tip> findById(long id);
}
