package com.tc_4.carbon_counter.controllers;

import java.util.Optional;

import com.tc_4.carbon_counter.databases.TipsDatabase;
import com.tc_4.carbon_counter.models.Tip;
import com.tc_4.carbon_counter.models.Tip.Status;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TipsController {

    private final TipsDatabase database;

    TipsController(TipsDatabase database){
        this.database = database;
    }
    @GetMapping("/tips/{title}")
    public Optional<Tip> getUserInfo(@PathVariable String title){
        return database.findByTitle(title);
    }
    
    @RequestMapping("/tips/{title}/setTitle")
    public Tip setTitle(@PathVariable String title, @RequestParam("newTitle") String newTitle)
    {
        database.findByTitle(title).get().setTitle(newTitle);
        //database.findByTitle(title).get().setStatus(Status.APPROVED);
        return database.save(database.findByWorkingTitle(title).get());    
    }
    
}
