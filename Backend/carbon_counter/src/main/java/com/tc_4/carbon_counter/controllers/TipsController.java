package com.tc_4.carbon_counter.controllers;

import java.util.Optional;

import com.tc_4.carbon_counter.databases.TipsDatabase;
import com.tc_4.carbon_counter.models.Tip;
import com.tc_4.carbon_counter.models.Tip.Catagory;
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
        //sets the working title to the new title and status to editing
        database.findByTitle(title).get().setTitle(newTitle);
        return database.save(database.findByTitle(title).get());    
    }
    @RequestMapping("/tips/{title}/setCatagory")
    public Tip setCatagory(@PathVariable String title, @RequestParam("newCatagory") Catagory newCatagory)
    {
        //sets the working catagory to the new catagory and status to editing
        database.findByTitle(title).get().setCatagory(newCatagory);
        return database.save(database.findByTitle(title).get());    
    }
    @RequestMapping("/tips/{title}/setBody")
    public Tip setBody(@PathVariable String title, @RequestParam("newBody") String newBody)
    {
        //sets the working body to the new body and status to editing
        database.findByTitle(title).get().setBody(newBody);
        return database.save(database.findByTitle(title).get());    
    }
    @RequestMapping("/tips/{title}/setCitation")
    public Tip setCitation(@PathVariable String title, @RequestParam("newCitation") String newCitation)
    {
        //sets the working citation to the new citation and status to editing
        database.findByTitle(title).get().setCitations(newCitation);
        return database.save(database.findByTitle(title).get());    
    }
    //need to work on
    //will need to make it check permissions to see if user can actually set the status
    @RequestMapping("/tips/{title}/setStatus")
    public boolean setTitle(@PathVariable String title, @RequestParam("newStatus") Status newStatus)
    {
        //returns true if it successfully set status else false
        long id = database.findByTitle(title).get().getId();
        if(newStatus == Status.APPROVED){
            database.findByTitle(title).get().setStatus(Status.APPROVED);
            //had to find by id as all other aspects may change when setting status
            database.save(database.findById(id).get());
            return true;
        }else if(newStatus == Status.DENIED){
            database.findByTitle(title).get().setStatus(Status.DENIED);
            //will set the all aspects of the tip to previous version that was approved if none were approved should be null
            database.save(database.findById(id).get());
            return true;
        }else if(newStatus == Status.EDITING){
            database.findByTitle(title).get().setStatus(Status.EDITING);
            //dont really need this tbh just have all statuses 
            return true;
        }else if(newStatus == Status.PENDING){
            database.findByTitle(title).get().setStatus(Status.PENDING);
            //also unsure if i need this
            return true;
        }else{
            return false;
        }    
    }
    
}
