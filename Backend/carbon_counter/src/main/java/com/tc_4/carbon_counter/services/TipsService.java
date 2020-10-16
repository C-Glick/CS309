package com.tc_4.carbon_counter.services;

import com.tc_4.carbon_counter.databases.TipsDatabase;
import com.tc_4.carbon_counter.models.Tip;
import com.tc_4.carbon_counter.models.Tip.Catagory;
import com.tc_4.carbon_counter.models.Tip.Status;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipsService {

    @Autowired
    private TipsDatabase tipsDatabase;


    public Tip getTipByTitle(String Title){
        //TODO
        //this works can actually get a response
        //need to tweak to see if that tip is approved and also create a find tips by catagory
        return tipsDatabase.findByTitle(Title).get();
    }
    public Tip[] getTipsByCatagory(Catagory catagory){
        //TODO
        //cant figure this out
        //keeps giving a error for the mapping i believe has to do with TipsDatabase
        return null;
    }
    public Tip addTip(JSONObject newTip){
        String title = newTip.getString("title");
        String body = newTip.getString("body");
        String catagory = newTip.getString("catagory");
        //next need to create a tip with these things
        //should probably make tip constructor

        //TODO
        
        return null;

    }
    public Tip editTip(String title, Catagory catagory, String body){
        //TODO
        return null;
    }
    public Tip setStatus(Status status){
        //TODO
        //need to check the permission of the user then set the status
        return null;
    }
    
}
