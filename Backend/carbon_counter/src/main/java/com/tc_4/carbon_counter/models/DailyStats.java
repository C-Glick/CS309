package com.tc_4.carbon_counter.models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.hibernate.annotations.UpdateTimestamp;

/**
 * Statics entry for tracking daily values for each user
 */
@Entity
public class DailyStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
 
    @Column(name="user_name")
    String userName;

    @Column(name="date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")    
    @UpdateTimestamp
    Date date;

    @Column(name="water")
    double water;

    @Column(name="power")
    double power;

    @Column(name="miles_driven")
    double milesDriven;

    @Column(name="meat")
    double meat;

    @Column(name="garbage")
    double garbage;

//getters----------------------------------

    public Long getId(){
        return id;
    }

    public String getUserName(){
        return userName;
    }

    public Date getDate(){
        return date;
    }

    public double getWater(){
        return water;
    }

    public double getPower(){
        return power;
    }

    public double getMilesDriven(){
        return milesDriven;
    }

    public double getMeat(){
        return meat;
    }

    public double getGarbage(){
        return garbage;
    }

//Setters----------------------------------

    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public void setWater(double water){
        this.water = water;
    }

    public void setPower(double power){
        this.power = power;
    }

    public void setMilesDriven(double milesDriven){
        this.milesDriven = milesDriven;
    }

    public void setMeat(double meat){
        this.meat = meat;
    }

    public void setGarbage(double garbage){
        this.garbage = garbage;
    }

}
