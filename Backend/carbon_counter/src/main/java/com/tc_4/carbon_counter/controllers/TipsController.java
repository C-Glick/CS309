package com.tc_4.carbon_counter.controllers;

import java.util.List;
import java.util.Optional;

import com.tc_4.carbon_counter.databases.TipsDatabase;
import com.tc_4.carbon_counter.models.Tip;
import com.tc_4.carbon_counter.models.Tip.Catagory;
import com.tc_4.carbon_counter.models.Tip.Status;
import com.tc_4.carbon_counter.services.TipsService;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TipsController {

    @Autowired
    private TipsService tipsService;

    @GetMapping("/tip/{title}")
    public Tip getTipByTitle(@PathVariable String title){
        //DONE
        return tipsService.getTipByTitle(title);
    }
    @GetMapping("/tips/{catagory}")
    public List<Tip> getTipsByCategory(@PathVariable Catagory catagory){
        //DONE
        return tipsService.getTipsByCatagory(catagory);
    }
    /**
     * 
     * @param newTip must be in a JSON format and send a title, body and catagory that are all non-null
     * @return
     */
    @RequestMapping("/tip/addTip")
    public Tip addTip(@RequestBody String newTip){
        //TODO

        return tipsService.addTip(newTip);
    }
    /**
     * 
     * @param title the title of the tip to be edited
     * @param edit the edited version of the tip in JSON format
     * @return
     */
    @RequestMapping("/tip/editTip/{title}")
    public Tip editTip(@PathVariable String title, @RequestBody String edit)
    {       
        //DONE
        return tipsService.editTip(title, edit);
    }
    @RequestMapping("/tip/setStatus/{title}")
    public Tip setStatus(@PathVariable String title, @RequestParam Status newStatus){
        //TODO
        return tipsService.setStatus(title, newStatus);
    }
    @RequestMapping("/allTips")
    public Tip allTips(@PathVariable String title, @RequestParam Status newStatus){
        //TODO
        //returns all tips in List
        return tipsService.setStatus(title, newStatus);
    }
    @RequestMapping("/tip/deleteTip/{title}")
    public boolean deleteTip(@PathVariable String title){
        //TODO
        return true;
    }
    /*
    @RequestMapping("/tips/{title}/setcatagory")
    public Tip setcatagory(@PathVariable String title, @RequestParam("newcatagory") catagory newcatagory)
    {
        //sets the working catagory to the new catagory and status to editing
        database.findByTitle(title).get().setcatagory(newcatagory);
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
    }*/
    
}
