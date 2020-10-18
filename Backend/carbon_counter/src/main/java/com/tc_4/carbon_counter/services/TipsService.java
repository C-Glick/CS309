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
    public Tip addTip(String newTip){
        JSONObject obj = new JSONObject(newTip);
        Tip temp = new Tip();
        String title = obj.getString("title");
        temp.setTitle(title);
        String body = obj.getString("body");
        temp.setBody(body);
        String catagory = obj.getString("catagory");
        temp.setCatagory(Catagory.valueOf(catagory));
        //this works need to change return type
        tipsDatabase.save(temp);
        return tipsDatabase.findByWorkingTitle(title).get();
    }
    public Tip editTip(String title, String JSONbody){
        JSONObject newTip = new JSONObject(JSONbody);
        Tip tempTip = tipsDatabase.findByTitle(title).get();
        //can easily expand to encompass all parts of tip except set status
        //only updates things sent in the JSON. if a JSON key is empty it breaks. Otherwise works well
        if(newTip.has("title") && !newTip.isNull("title")){
            tempTip.setTitle(newTip.getString("title"));
        }
        if(newTip.has("body") && !newTip.isNull("body")){
            tempTip.setBody(newTip.getString("body")); 
        }
        if(newTip.has("catagory") && !newTip.isNull("catagory")){
            tempTip.setCatagory(Catagory.valueOf(newTip.getString("catagory")));
        }
        //does actually save and sets the working title, body and catagory of the tip.
        return tipsDatabase.save(tempTip);
    }
    public Tip setStatus(Status status){
        //TODO
        //need to check the permission of the user then set the status
        return null;
    }
    
}
