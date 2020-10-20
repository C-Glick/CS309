package com.tc_4.carbon_counter.databases;

import java.util.List;
import java.util.Optional;

import com.tc_4.carbon_counter.models.Tip;
import com.tc_4.carbon_counter.models.Tip.Catagory;
import com.tc_4.carbon_counter.models.Tip.Status;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TipsDatabase extends JpaRepository<Tip, Long> {
    
    Optional<Tip> findByTitle(String title);
    Optional<Tip> findByWorkingTitle(String workingTitle);
    List<Tip> findByCatagoryAndStatus(Catagory catagory,Status status);
    //Optional<Tip> findByCatagory(Catagory catagory);
    Optional<Tip> findById(Long id);
}
