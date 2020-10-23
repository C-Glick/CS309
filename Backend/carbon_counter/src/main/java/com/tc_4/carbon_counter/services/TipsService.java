package com.tc_4.carbon_counter.services;

import java.util.List;

import com.tc_4.carbon_counter.databases.TipsDatabase;
import com.tc_4.carbon_counter.exceptions.TipNotFoundException;
import com.tc_4.carbon_counter.exceptions.TitleTakenException;
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
    /**
     * 
     * @param Title the title of the tip you would like
     * @return the tip that was found from that title. This will only give you approved tips
     */
    public Tip getTipByTitle(String Title) {
        //DONE
        if(tipsDatabase.findByTitleAndStatus(Title, Status.APPROVED).isPresent()){
            return tipsDatabase.findByTitleAndStatus(Title, Status.APPROVED).get();
        }
        throw new TipNotFoundException(Title);
        
    }
    /**
     * 
     * @param Title the working title of the tip
     * @return the tip with that working title
     */
    public Tip getTipByWorkingTitle(String Title) {
        //DONE
        if(tipsDatabase.findByWorkingTitle(Title).isPresent()){
            return tipsDatabase.findByWorkingTitle(Title).get();
        }
        throw new TipNotFoundException(Title);
        
    }
    
    /**
     * 
     * @param catagory the catagory of the tips
     * @return all approved tips in that catagory
     */
    public List<Tip> getTipsByCatagory(Catagory catagory) {
        //DONE
        List<Tip> tips = tipsDatabase.findByCatagoryAndStatus(catagory, Status.APPROVED);//this works now
        return tips;
    }
    /**
     * 
     * @param newTip The tip in the JSON format
     * @return the new tip in the database
     */
    public Tip addTip(String newTip) {
        //DONE
        JSONObject obj = new JSONObject(newTip);
        Tip temp = new Tip();
        String title = obj.getString("title");
        if(tipsDatabase.findByTitle(title).isPresent() || tipsDatabase.findByWorkingTitle(title).isPresent()){
            throw new  TitleTakenException(title);
        }
        temp.setTitle(title);
        String body = obj.getString("body");
        temp.setBody(body);
        String catagory = obj.getString("catagory");
        temp.setCatagory(Catagory.valueOf(catagory));
        tipsDatabase.save(temp);
        return tipsDatabase.findByWorkingTitle(title).get();
    }
    /**
     * 
     * @param title the title of the tip to edit
     * @param JSONbody the new parts of the tip that need edited in a json format
     * @return
     */
    public Tip editTip(String title, String JSONbody) {
        //DONE
        JSONObject newTip = new JSONObject(JSONbody);
        Tip tempTip = tipsDatabase.findByTitle(title).get();
        // can easily expand to encompass all parts of tip except set status
        // only updates things sent in the JSON. if a JSON key is empty it breaks.
        // Otherwise works well
        if (newTip.has("title") && !newTip.isNull("title")) {
            if(tipsDatabase.findByTitle(newTip.getString("title")).isPresent() || tipsDatabase.findByWorkingTitle(newTip.getString("title")).isPresent()){
                throw new  TitleTakenException(title);
            }
            tempTip.setTitle(newTip.getString("title"));
        }
        if (newTip.has("body") && !newTip.isNull("body")) {
            tempTip.setBody(newTip.getString("body"));
        }
        if (newTip.has("catagory") && !newTip.isNull("catagory")) {
            tempTip.setCatagory(Catagory.valueOf(newTip.getString("catagory")));
        }
        tipsDatabase.save(tempTip);
        return tipsDatabase.findByTitle(title).get();
    }
    /**
     * 
     * @param title the working title of the tip
     * @param status the new status of the tip either APPROVED, PENDING, DENIED or EDITING
     * @return
     */
    public Tip setStatus(String title, Status status){
        //DONE
        //need to check the permission of the user then set the status
        tipsDatabase.findByWorkingTitle(title).get().setStatus(status);
        return tipsDatabase.save(tipsDatabase.findByWorkingTitle(title).get());
    }
    /**
     * 
     * @param title the working title of the tip
     * @return true if it is deleted else false
     */
    public Boolean deleteTipByWorkingTitle(String title){
        //DONE
        //should also probably have permissions
        if(tipsDatabase.findByWorkingTitle(title).isPresent()){
            tipsDatabase.delete(tipsDatabase.findByWorkingTitle(title).get());
            return true;
        }
        return false;
    }
    /**
     * 
     * @return all approved tips
     */
    public List<Tip> allTipsApproved(){
        //DONE
        return tipsDatabase.findByStatus(Status.APPROVED);
    }
    /**
     * 
     * @return all unapproved tips
     */
    public List<Tip> allTips(){
        //DONE
        List<Tip> tips = tipsDatabase.findByStatus(Status.DENIED);
        tips.addAll(tipsDatabase.findByStatus(Status.EDITING)); 
        tips.addAll(tipsDatabase.findByStatus(Status.PENDING));
        return tips;
    }
    
}
